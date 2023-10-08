from os import environ as env
import re
from OTXv2 import OTXv2, IndicatorTypes
from PostgreService import PostgreService

class OTXService:
    def __init__(self):
        self.API_KEY = env["OTX_API_KEY"]
        self.otx = OTXv2(self.API_KEY)

    def getValue(self, results, keys):
        if type(keys) is list and len(keys) > 0:
            if type(results) is dict:
                key = keys.pop(0)
                if key in results:
                    return self.getValue(results[key], keys)
                else:
                    return None
            else:
                if type(results) is list and len(results) > 0:
                    return self.getValue(results[0], keys)
                else:
                    return results
        else:
            return results

    def ip(self, ip):
        alerts = []
        result = self.otx.get_indicator_details_by_section(IndicatorTypes.IPv4, ip, 'general')

        validation = self.getValue(result, ['validation'])
        if not validation:
            pulses = self.getValue(result, ['pulse_info', 'pulses'])
            if pulses:
                for pulse in pulses:
                    if 'name' in pulse and 'description' in pulse:
                        alerts.append({"pulse": pulse['name'], "pulse_descr": pulse['description']})

        return alerts

    def hostname(self, hostname):
        alerts = []
        result = self.otx.get_indicator_details_by_section(IndicatorTypes.HOSTNAME, hostname, 'general')

        validation = self.getValue(result, ['validation'])
        if not validation:
            pulses = self.getValue(result, ['pulse_info', 'pulses'])
            if pulses:
                for pulse in pulses:
                    if 'name' in pulse and 'description' in pulse:
                        alerts.append({"pulse": pulse['name'], "pulse_descr": pulse['description']})

        return alerts

    def url(self, url):
        alerts = []
        result = self.otx.get_indicator_details_full(IndicatorTypes.URL, url)
        
        google = self.getValue( result, ['url_list', 'url_list', 'result', 'safebrowsing'])
        if google and 'response_code' in str(google):
            alerts.append({"pulse": 'google_safebrowsing', "pulse_descr": 'malicious'})

        clamav = self.getValue( result, ['url_list', 'url_list', 'result', 'multiav','matches','clamav'])
        if clamav:
                alerts.append({"pulse": 'clamav', "pulse_descr": clamav})

        avast = self.getValue( result, ['url_list', 'url_list', 'result', 'multiav','matches','avast'])
        if avast:
            alerts.append({"pulse": 'avast', "pulse_descr": avast})

        return alerts

    def file_hash(self, hash):
        alerts = []

        hash_type = IndicatorTypes.FILE_HASH_MD5
        if len(hash) == 64:
            hash_type = IndicatorTypes.FILE_HASH_SHA256
        if len(hash) == 40:
            hash_type = IndicatorTypes.FILE_HASH_SHA1

        result = self.otx.get_indicator_details_full(hash_type, hash)

        avg = self.getValue( result, ['analysis','analysis','plugins','avg','results','detection'])
        if avg:
            alerts.append({"pulse": 'avg', "pulse_descr": avg})

        clamav = self.getValue( result, ['analysis','analysis','plugins','clamav','results','detection'])
        if clamav:
            alerts.append({"pulse": 'clamav', "pulse_descr": clamav})

        avast = self.getValue( result, ['analysis','analysis','plugins','avast','results','detection'])
        if avast:
            alerts.append({"pulse": 'avast', "pulse_descr": avast})

        microsoft = self.getValue( result, ['analysis','analysis','plugins','cuckoo','result','virustotal','scans','Microsoft','result'])
        if microsoft:
            alerts.append({"pulse": 'microsoft', "pulse_descr": microsoft})

        symantec = self.getValue( result, ['analysis','analysis','plugins','cuckoo','result','virustotal','scans','Symantec','result'])
        if symantec:
            alerts.append({"pulse": 'symantec', "pulse_descr": symantec})

        kaspersky = self.getValue( result, ['analysis','analysis','plugins','cuckoo','result','virustotal','scans','Kaspersky','result'])
        if kaspersky:
            alerts.append({"pulse": 'kaspersky', "pulse_descr": kaspersky})

        suricata = self.getValue( result, ['analysis','analysis','plugins','cuckoo','result','suricata','rules','name'])
        if suricata and 'trojan' in str(suricata).lower():
            alerts.append({"pulse": 'suricata', "pulse_descr": suricata})

        return alerts

    def phrase(self, key, max_results=5):
        alerts = []
        search_pulses_url = self.otx.create_url("/api/v1/search/pulses", q=key, page=1, sort="-modified", limit=25)
        result = self.otx._get_paginated_resource(search_pulses_url, max_results=max_results)

        for res in result['results']:
            alerts.append({"pulse": res['name'], "pulse_descr": res['description']})
        
        return alerts

    def ask(self, q, askedBy, postgre_service):
        
        if re.search("^(\d{1,3}\.){0,3}\d{0,10}$", q):
            alerts = self.ip(q)
            query_type = "ip"
        
        elif re.search("^([A-Za-z0-9-]+)(\.[A-Za-z]+)+$", q):
            alerts = self.hostname(q)
            query_type = "domain"
            
        elif re.search("^https?:\/\/[A-Za-z0-9./\-]+$", q):
            alerts = self.url(q)
            query_type = "url"
            
        elif len(q) in [32, 40, 64]:
            alerts = self.file_hash(q)
            query_type = "hash"

        else:
            alerts = self.phrase(q)
            query_type = "phrase"
            
        postgre_service.insertResult({"threat": q, "type": query_type, "submittedBy": askedBy, "alerts": alerts})
        print(f"Job {q} processed")