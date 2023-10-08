from os import environ as env

from flask import Flask, request
from flask_cors import CORS
from flask_bcrypt import Bcrypt

from RedisService import RedisService
from AuthenticationService import AuthenticationService
from PostgreService import PostgreService
from OTXService import OTXService

app = Flask(__name__)
CORS(app)

USERNAME = "GUEST"

redis_service = RedisService()
auth_service = AuthenticationService(app)
postgre_service = PostgreService(app)
otx_service = OTXService()
app.app_context().push()

with app.app_context():
    redis_service.create_workers(4)

@app.route("/")
def index():
    return {"STATUS": "SUCCESS"}

@app.post("/register")
def register():
    return postgre_service.createUser(request.json, auth_service)
    
@app.post("/login")
def login():
    return auth_service.login(request.json)

@app.post("/uploadFile")
def postFile():
    f = request.files['file']
    counter = 0
    for line in f:
        q = line.decode().strip()
        redis_service.enqueue_job(otx_service, postgre_service, q, USERNAME)
        counter +=1
    
    return {"Jobs Queued": counter}

@app.get("/threats")
def getThreats():
    return postgre_service.readThreats()

@app.get("/unknowns")
def getUnknowns():
    return postgre_service.readUnknowns()

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8080, debug=True)