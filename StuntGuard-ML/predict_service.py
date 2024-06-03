from flask import Flask, request, jsonify
import numpy as np
import mysql.connector
import os
from datetime import datetime
from dotenv import load_dotenv
import tensorflow as tf

env_path = './.env'
load_dotenv(dotenv_path=env_path)
app = Flask(__name__)

# Load model
model = tf.keras.models.load_model("tuned_model.h5")
print(type(model))


# Fungsi untuk menghubungkan ke database MySQL
def get_mysql_connection():
    return mysql.connector.connect(
        host=os.getenv('DB_HOST'),
        user=os.getenv('DB_USER'),
        password=os.getenv('DB_PASS'),
        database=os.getenv('DB_NAME'),
    )

@app.route("/")
def home():
    message = "StuntGuard Predict API"
    return jsonify(message)

@app.route("/check_model_attributes", methods=["GET"])
def check_model_attributes():
    try:
        # Check model attributes
        model_attributes = dir(model)
        return jsonify({"model_attributes": model_attributes})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

PREDICTION_LABELS = [
    "predict_id",
    "child_weight",
    "child_height",
    "predict_result",
    "child_id",
    "created_at",
    "updated_at",
    "confidence_score",
    "breastfeeding" 
]

# Convert the fetched data to a list of dictionaries with labels
def label_predictions(predictions):
    return [
        {PREDICTION_LABELS[i]: value for i, value in enumerate(prediction)}
        for prediction in predictions
    ]

@app.route("/predict/<child_id>", methods=["POST"])
def predict(child_id):
    try:
        # Connect to MySQL and fetch child data
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM Child WHERE child_id = %s", (child_id,))
        child_data = cursor.fetchone()
        conn.close()

        if not child_data:
            return jsonify({"error": "Child data not found."}), 404

        # Parse request data
        print("data child =", child_data)

        data = request.json
        child_weight = data.get("child_weight")
        child_height = data.get("child_height")

        # Allow user to override breastfeeding value
        breastfeeding = data.get("breastfeeding")
        breastfeeding = 1 if breastfeeding == "Yes" else 0
        default_breastfeeding = 1 if child_data[8] == "Yes" else 0

        if breastfeeding is not None and breastfeeding != default_breastfeeding:
            # Connect to MySQL and update breastfeeding value
            conn = get_mysql_connection()
            cursor = conn.cursor()
            cursor.execute(
                "UPDATE Child SET breastfeeding = %s WHERE child_id = %s",
                (breastfeeding, child_id),
            )
            conn.commit()  # Commit the changes to the database
            conn.close()  # Close the connection after committing
            print("Breastfeeding value updated successfully.")

        print("data child 2 =", child_weight, child_height, breastfeeding)

        # Prepare data for prediction
        prediction_data = {
            "Gender": 1 if child_data[3] == "Male" else 0,
            "Age": child_data[5],
            "Birth Weight": child_data[6],
            "Birth Length": child_data[7],
            "Body Weight": child_weight,
            "Body Length": child_height,
            "Breastfeeding": breastfeeding,
        }

        print("data predict =", prediction_data)

        # Perform prediction with the model
        prediction_array = np.array([list(prediction_data.values())])
        print(model.predict)
        if hasattr(model, "predict"):
            print("predict")
            prediction = model.predict(prediction_array)
            print("predict lagi", prediction)
            confidence_score = float(prediction[0][0])
            print("confidence_score", confidence_score)
            predict_result = bool(
                confidence_score > 0.5
            )  # Assuming threshold is 0.5 for binary classification
            print(predict_result)
        else:
            return jsonify({"error": "Model does not have predict method."}), 500

        # Generate predict_id and current timestamp
        predict_id = datetime.now().strftime("%d%m%Y")
        print("predict id =", predict_id)
        created_at = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        print("created_at =", created_at)

        # Connect to MySQL and insert or update prediction data
        conn = get_mysql_connection()
        cursor = conn.cursor()

        # Check if a prediction for the current date already exists
        cursor.execute(
            "SELECT * FROM StuntPredict WHERE predict_id = %s", (predict_id,)
        )
        existing_prediction = cursor.fetchone()
        print("existing pred :", existing_prediction)
        if existing_prediction:
            # Update existing prediction
            print("existing pred true")
            cursor.execute(
                "UPDATE StuntPredict SET child_weight = %s, child_height = %s, predict_result = %s, created_at = %s, confidenceScore = %s WHERE predict_id = %s",
                (
                    child_weight,
                    child_height,
                    predict_result,
                    created_at,
                    confidence_score,
                    predict_id,
                ),
            )
        else:
            # Insert new prediction
            print("existing pred false")
            cursor.execute(
                "INSERT INTO StuntPredict (predict_id, child_weight, child_height, predict_result, child_id, created_at, confidenceScore) VALUES (%s, %s, %s, %s, %s, %s, %s)",
                (
                    predict_id,
                    child_weight,
                    child_height,
                    predict_result,
                    child_id,
                    created_at,
                    confidence_score,
                ),
            )

        print("don 1")
        conn.commit()
        print("don 2")

        cursor.close()
        conn.close()

        # Prepare response data
        response_data = {
            "predict_id": predict_id,
            "child_weight": child_weight,
            "child_height": child_height,
            "predict_result": predict_result,
            "created_at": created_at,
            "confidenceScore": confidence_score,
            "child_id": child_id,
            "breastfeeding": "Yes" if breastfeeding == 1 else "No"
        }

        return jsonify(response_data)

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Get all predictions
@app.route("/predict/all", methods=["GET"])
def get_all_predictions():
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM StuntPredict")
        predictions = cursor.fetchall()
        conn.close()

        labeled_predictions = label_predictions(predictions)

        return (
            jsonify(
                {
                    "status": True,
                    "message": "All predictions retrieved successfully.",
                    "data": labeled_predictions,
                }
            ),
            200,
        )

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Get predictions by id
@app.route("/predict/id/<predict_id>", methods=["GET"])
def get_prediction_by_id(predict_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT * FROM StuntPredict WHERE predict_id = %s", (predict_id,)
        )
        prediction = cursor.fetchone()
        conn.close()

        if not prediction:
            return jsonify({"error": "Prediction not found."}), 404

        labeled_prediction = label_predictions([prediction])[0]

        return (
            jsonify(
                {
                    "status": True,
                    "message": "Prediction retrieved successfully.",
                    "data": labeled_prediction,
                }
            ),
            200,
        )

    except Exception as e:
        return jsonify({"error": str(e)}), 500


# Get all predictions by child
@app.route("/predict/child/<child_id>", methods=["GET"])
def get_predictions_by_child(child_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM StuntPredict WHERE child_id = %s", (child_id,))
        predictions = cursor.fetchall()
        conn.close()

        if not predictions:
            return jsonify({"error": "No predictions found for this child."}), 404

        labeled_predictions = label_predictions(predictions)

        return (
            jsonify(
                {
                    "status": True,
                    "message": "All predictions for the child retrieved successfully.",
                    "data": labeled_predictions,
                }
            ),
            200,
        )

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=os.getenv("PORT"))
