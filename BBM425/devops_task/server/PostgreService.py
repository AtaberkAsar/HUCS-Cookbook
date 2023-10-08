from os import environ as env
from uuid import uuid4
from datetime import datetime

from flask import Flask, jsonify, current_app
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.exc import InvalidRequestError

DATABASE = SQLAlchemy()

def _get_uuid():
    return uuid4().hex

class UserModel(DATABASE.Model):
    __tablename__ = "users"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    name = DATABASE.Column(DATABASE.String(50), nullable=False)
    email = DATABASE.Column(DATABASE.String(100), unique=True)
    password = DATABASE.Column(DATABASE.Text, nullable=False)
    
    def __repr__(self):
        return f"name: {self.name}\nemail: {self.email}"
    
    def json(self):
        return jsonify({
            "id": self.id,
            "name": self.name,
            "email": self.email
        })

class ThreatModel(DATABASE.Model):
    __tablename__ = "known_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    type = DATABASE.Column(DATABASE.Text)
    threat = DATABASE.Column(DATABASE.Text)
    submittedBy = DATABASE.Column(DATABASE.Text)
    timestamp = DATABASE.Column(DATABASE.DateTime, default=datetime.utcnow)
    
    def json(self):
        return jsonify({
            "type": self.type,
            "threat": self.threat,
            "submittedBy": self.submittedBy,
            "timestamp": self.timestamp
        })

class UnknownModel(DATABASE.Model):
    __tablename__ = "unknown_queries"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    type = DATABASE.Column(DATABASE.Text)
    threat = DATABASE.Column(DATABASE.Text)
    submittedBy = DATABASE.Column(DATABASE.Text)
    timestamp = DATABASE.Column(DATABASE.DateTime, default=datetime.utcnow)
    
    def json(self):
        return jsonify({
            "type": self.type,
            "threat": self.threat,
            "submittedBy": self.submittedBy,
            "timestamp": self.timestamp
        })

class IpThreat(DATABASE.Model):
    __tablename__ = "ip_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    ip = DATABASE.Column(DATABASE.String(15))
    pulse = DATABASE.Column(DATABASE.Text)
    pulse_descr = DATABASE.Column(DATABASE.Text)
    
    def json(self):
        return jsonify({
            "id": self.id,
            "ip": self.ip,
            "pulse": self.pulse,
            "pulse_descr": self.pulse_descr
        })

class DomainThreat(DATABASE.Model):
    __tablename__ = "domain_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    domain = DATABASE.Column(DATABASE.Text)
    pulse = DATABASE.Column(DATABASE.Text)
    pulse_descr = DATABASE.Column(DATABASE.Text)
    
    def json(self):
        return jsonify({
            "id": self.id,
            "domain": self.domain,
            "pulse": self.pulse,
            "pulse_descr": self.pulse_descr
        })
        
class UrlThreat(DATABASE.Model):
    __tablename__ = "url_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    url = DATABASE.Column(DATABASE.Text)
    pulse = DATABASE.Column(DATABASE.Text)
    pulse_descr = DATABASE.Column(DATABASE.Text)
    
    def json(self):
        return jsonify({
            "id": self.id,
            "url": self.url,
            "pulse": self.pulse,
            "pulse_descr": self.pulse_descr
        })

class HashThreat(DATABASE.Model):
    __tablename__ = "hash_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    hash = DATABASE.Column(DATABASE.Text)
    pulse = DATABASE.Column(DATABASE.Text)
    pulse_descr = DATABASE.Column(DATABASE.Text)
    
    def json(self):
        return jsonify({
            "id": self.id,
            "hash": self.hash,
            "pulse": self.pulse,
            "pulse_descr": self.pulse_descr
        })

class PhraseThreat(DATABASE.Model):
    __tablename__ = "phrase_threats"
    
    id = DATABASE.Column(DATABASE.String(32), primary_key=True, unique=True, default=_get_uuid)
    phrase = DATABASE.Column(DATABASE.Text)
    pulse = DATABASE.Column(DATABASE.Text)
    pulse_descr = DATABASE.Column(DATABASE.Text)
    
    def json(self):
        return jsonify({
            "id": self.id,
            "phrase": self.phrase,
            "pulse": self.pulse,
            "pulse_descr": self.pulse_descr
        })

class PostgreService:
    def __init__(self, app: Flask):
        host = env["POSTGRES_HOST"]
        database = env["POSTGRES_DB"]
        user = env["POSTGRES_USER"]
        password = env["POSTGRES_PASSWORD"]
        port = env["POSTGRES_PORT"]

        app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://{user}:{password}@{host}:{port}/{database}"
        
        DATABASE.init_app(app)
        with app.app_context():
            DATABASE.create_all()

    def createUser(self, user, authService):
        user_exists = UserModel.query.filter_by(email=user["email"]).first() is not None
        if user_exists:
            return jsonify({"error": "EMAIL IN USE"}), 409
        
        password = authService.hash_password(user["password"])
        new_user = UserModel(name=user["name"], email=user["email"], password=password)
        DATABASE.session.add(new_user)
        DATABASE.session.commit()
        
        return new_user.json()
        
    def createThreat(self, threat_info):
        t = ThreatModel.query.filter_by(threat=threat_info["threat"]).first()
        if t is not None:
            t.timestamp = datetime.utcnow()
            DATABASE.session.commit()
            return t.json()
        
        u = UnknownModel.query.filter_by(threat=threat_info["threat"]).first()
        if u is not None:
            DATABASE.session.delete(u)
            DATABASE.session.commit()
        
        new_threat = ThreatModel(threat=threat_info["threat"], type=threat_info["type"], submittedBy=threat_info["submittedBy"], timestamp=datetime.utcnow())
        DATABASE.session.add(new_threat)
        DATABASE.session.commit()
        
        match new_threat.type:
            case "ip":
                for i in threat_info["alerts"]:
                    pulse, pulse_descr = i["pulse"], i["pulse_descr"]
                    new_ip = IpThreat(ip=new_threat.threat, pulse=pulse, pulse_descr=pulse_descr)
                    DATABASE.session.add(new_ip)
            case "domain":
                for i in threat_info["alerts"]:
                    pulse, pulse_descr = i["pulse"], i["pulse_descr"]
                    new_domain = DomainThreat(domain=new_threat.threat, pulse=pulse, pulse_descr=pulse_descr)
                    DATABASE.session.add(new_domain)
            case "url":
                for i in threat_info["alerts"]:
                    pulse, pulse_descr = i["pulse"], i["pulse_descr"]
                    new_url = UrlThreat(url=new_threat.threat, pulse=pulse, pulse_descr=pulse_descr)
                    DATABASE.session.add(new_url)
            case "hash":
                for i in threat_info["alerts"]:
                    pulse, pulse_descr = i["pulse"], i["pulse_descr"]
                    new_hash = HashThreat(hash=new_threat.threat, pulse=pulse, pulse_descr=pulse_descr)
                    DATABASE.session.add(new_hash)
            case "phrase":
                for i in threat_info["alerts"]:
                    pulse, pulse_descr = i["pulse"], i["pulse_descr"]
                    new_phrase = HashThreat(phrase=new_threat.threat, pulse=pulse, pulse_descr=pulse_descr)
                    DATABASE.session.add(new_phrase)
            case _:
                return jsonify({"error": f"UNKNOWN THREAT TYPE: {new_threat.type}"}), 500
        
        DATABASE.session.commit()
        return new_threat.json()

    def createUnknown(self, unknow):
        u = UnknownModel.query.filter_by(threat=unknow["threat"]).first()
        if u is not None:
            u.timestamp = datetime.utcnow()
            DATABASE.session.commit()
            return u.json()
        
        new_unknown = ThreatModel(threat=unknow["threat"], type=unknow["type"], submittedBy=unknow["submittedBy"], timestamp=datetime.utcnow())
        DATABASE.session.add(new_unknown)
        DATABASE.session.commit()
        
        return new_unknown.json()

    def insertResult(self, result):
        try:
            with DATABASE.session.begin():
                if result["alerts"]:
                    self.createThreat(result)
                else:
                    self.createUnknown(result)
        except InvalidRequestError as e:
            pass
        
    def readThreats(self):
        data = []
        for i in ThreatModel.query.all():
            data.append([i.id, i.type, i.threat, i.submittedBy, i.timestamp])
        response = jsonify({"data": data})
        response.headers.add('Access-Control-Allow-Origin', '*')
        return response

    def readUnknowns(self):
        return jsonify({"data": UnknownModel.query.all()})

# def insert_alert(result):
#     with conn:
#         with conn.cursor() as cursor:
#             for alert in result["alerts"]:
#                 cursor.execute("INSERT INTO results (q, alert_name, alert_description) VALUES (%s, %s, %s);", (result["q"], alert["name"], alert["description"]))

# def select_all_alerts():
#     with conn:
#         with conn.cursor() as cursor:
#             cursor.execute("SELECT * FROM results")
#             ls = cursor.fetchall()
    
#     return {"count": len(ls), "data": ls}

