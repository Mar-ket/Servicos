import os
TESTING = False
DEBUG = True
FLASK_ENV = 'development'
SECRET_KEY = 'GDtfDCFYjD'
REDIS_URL = os.getenv("REDIS_CN_STRING") or "redis://:@redis_db:6379/0"
BASE_URL = "/api/v1"
PARSE_URL = os.getenv("PARSE") or "http://parse:1337"
print(os.listdir("/tmp/secrets/auth-jwt-secret"))

with open("/tmp/secrets/auth-jwt-secret/auth-jwt-secret", 'r') as f:
    JWT_SECRET = f.read()
    print(f"Secret Key: {JWT_SECRET}")
with open("/tmp/secrets/auth-app-secret/auth-app-secret", 'r') as f:
    SECRET_KEY = f.read()
    print(f"Secret Key: {SECRET_KEY}")
