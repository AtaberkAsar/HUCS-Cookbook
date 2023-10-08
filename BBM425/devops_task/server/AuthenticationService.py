from flask import Flask, jsonify
from flask_bcrypt import Bcrypt

from PostgreService import UserModel

class AuthenticationService:
    
    def __init__(self, app: Flask):
        self.bcrypt = Bcrypt(app)
    
    def hash_password(self, password):
        return self.bcrypt.generate_password_hash(password).decode()
    
    def login(self, user_cred):
        user = UserModel.query.filter_by(email=user_cred["email"]).first()
        if user is None:
            return jsonify({"error": "INVALID EMAIL"}), 401
        
        if not self.bcrypt.check_password_hash(user.password, user_cred["password"]):
            return jsonify({"error": "INVALID PASSWORD"}), 401
        
        return user.json()