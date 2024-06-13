from flask import jsonify, request
from services.predict import *
import services.predict as predict
from services.recommendations import *
import services.recommendations as recom

def configure_routes(app):

    @app.route("/")
    def home():
        message = "Welcome to our machine learning model endpoints."
        return jsonify(message)

    @app.route('/check_model_attributes', methods=['GET'])
    def check_model_attributes_route():
        return recom.check_model_attributes()

    @app.route('/predict/<child_id>', methods=['POST'])
    def predict_route(child_id):
        return predict.post_predict(child_id)

    @app.route("/predict/all", methods=["GET"])
    def get_all_predictions_route():
        return predict.get_all_predictions()

    @app.route("/predict/id/<predict_id>", methods=["GET"])
    def get_prediction_by_id_route(predict_id):
        return predict.get_prediction_by_id(predict_id)

    @app.route("/predict/child/<child_id>", methods=["GET"])
    def get_predictions_by_child_route(child_id):
        return predict.get_predictions_by_child(child_id)
    
    @app.route("/notes/<predict_id>", methods=["POST"])
    def post_notes_route(predict_id):
        return predict.post_notes(predict_id)
    
    @app.route('/get_notes/<predict_id>', methods=['GET'])
    def get_notes_route(predict_id):
        return predict.get_notes(predict_id)

    @app.route('/recom/<predict_id>', methods=['POST'])
    def post_recom_route(predict_id):
        return recom.post_recom(predict_id)
    
    @app.route('/recom/all', methods=['GET'])
    def get_all_recommendations_route():
        return recom.get_all_recommendations()
    
    @app.route('/recom/child/<child_id>', methods=['GET'])
    def get_recommendations_by_child_route(child_id):
        return recom.get_recommendations_by_child(child_id)
    
    @app.route('/recom/id/<recommendation_id>/foods', methods=['GET'])
    def get_food_details_by_recommendation_route(recommendation_id):
        return recom.get_food_details_by_recommendation(recommendation_id)
    
    # @app.route('/register', methods=['POST'])
    # def register():
    #     return request ke "https://stuntguard-api-hz4azdtnzq-et.a.run.app/register"