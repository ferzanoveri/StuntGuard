from flask import Flask
from routes.routes import *
import routes.routes as routes
import os
from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)
routes.configure_routes(app)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=os.getenv("PORT"))
