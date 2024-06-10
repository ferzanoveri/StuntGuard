from flask import Flask, request, jsonify
import numpy as np
from nanoid import generate
from datetime import datetime
import tensorflow as tf
from db import *

# Load model
# Dapatkan path direktori saat ini dari file ini
current_dir = os.path.dirname(os.path.abspath(__file__))

# Path relatif ke file model h5
model_path = os.path.join(current_dir,"stunting_prediction_model.h5")
model = tf.keras.models.load_model(model_path)
print(type(model))

def home():
    message = "StuntGuard Predict API"
    return jsonify(message)


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
    "breastfeeding",
]


# Convert the fetched data to a list of dictionaries with labels
def label_predictions(predictions):
    return [
        {PREDICTION_LABELS[i]: value for i, value in enumerate(prediction)}
        for prediction in predictions
    ]

def post_predict(child_id):
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
        data = request.json
        child_weight = float(data.get("child_weight"))
        child_height = float(data.get("child_height"))

        # Handle breastfeeding value
        input_breastfeeding = data.get("breastfeeding")
        if input_breastfeeding == "Yes":
            breastfeeding = True
        elif input_breastfeeding == "No":
            breastfeeding = False
        else:
            breastfeeding = bool(child_data[8])  # Use default value from Child data

        # Prepare data for prediction
        prediction_data = {
            # "Gender": 1 if child_data[3] else "Female",
            "Gender": child_data[3],
            "Age": child_data[5],
            "Birth Weight": child_data[6],
            "Birth Length": child_data[7],
            "Body Weight": child_weight,
            "Body Length": child_height,
            "Breastfeeding": 1 if breastfeeding else 0,
            # "Breastfeeding": breastfeeding
        }
        print(prediction_data)

        # Perform prediction with the model
        prediction_array = np.array([list(prediction_data.values())])
        print(prediction_array)
        if hasattr(model, "predict"):
            prediction = model.predict(prediction_array)
            print(prediction)
            confidence_score = float(prediction[0][0])
            print(confidence_score)
            predict_result = (
                confidence_score > 0.5
            )  # Assuming threshold is 0.5 for binary classification
        else:
            return jsonify({"error": "Model does not have predict method."}), 500

        if predict_result:
            if prediction_data['Gender'] == 1:
                energy = float((0.167 * prediction_data['Body Weight']) + (15.174 * prediction_data['Body Length']) - 617.6)
            else:
                energy = float((16.252 * prediction_data['Body Weight']) + (1.618 * prediction_data['Body Length']) - 413.5)
            protein_calories = float(0.15 * energy)
            protein_grams = float(protein_calories / 4)
        else:
            if prediction_data['Gender'] == 1:
                energy = float((0.167 * prediction_data['Body Weight']) + (15.174 * prediction_data['Body Length']) - 617.6)
            else:
                energy = float((16.252 * prediction_data['Body Weight']) + (1.618 * prediction_data['Body Length']) - 413.5)
            protein_calories = float(0.15 * energy)
            protein_grams = float(protein_calories / 4)

        protein = protein_grams
        energy = energy

        print(protein, energy)
        # Generate predict_id and current timestamp
        predict_id = generate(size=8)
        created_at = datetime.now().strftime("%Y-%m-%d")
        updated_at = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

        # Connect to MySQL and insert or update prediction data
        conn = get_mysql_connection()
        cursor = conn.cursor()

        # Check if a prediction for the current date and child_id already exists
        cursor.execute(
            "SELECT * FROM StuntPredict WHERE DATE(created_at) = %s AND child_id = %s", (created_at, child_id)
        )
        existing_prediction = cursor.fetchone()
        if existing_prediction:
            # Update existing prediction
            predict_id = existing_prediction[0]  # Use the existing predict_id
            cursor.execute(
                "UPDATE StuntPredict SET child_weight = %s, child_height = %s, predict_result = %s, confidenceScore = %s, protein = %s, energy = %s, updated_at = %s WHERE predict_id = %s AND child_id = %s",
                (
                    child_weight,
                    child_height,
                    predict_result,
                    confidence_score,
                    protein,
                    energy,
                    updated_at,
                    predict_id,
                    child_id,
                ),
            )
        else:
            # Insert new prediction
            cursor.execute(
                "INSERT INTO StuntPredict (predict_id, child_weight, child_height, predict_result, child_id, created_at, confidenceScore, protein, energy, updated_at) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                (
                    predict_id,
                    child_weight,
                    child_height,
                    predict_result,
                    child_id,
                    created_at,
                    confidence_score,
                    protein,
                    energy,
                    updated_at,
                ),
            )

        # Update breastfeeding value in Child table
        cursor.execute(
            "UPDATE Child SET breastfeeding = %s WHERE child_id = %s",
            (breastfeeding, child_id),
        )

        conn.commit()
        cursor.close()
        conn.close()

        # Prepare response data
        response_data = {
            "predict_id": predict_id,
            "child_weight": child_weight,
            "child_height": child_height,
            "predict_result": "Yes" if predict_result else "No",
            "created_at": created_at,
            "updated_at": updated_at,
            "confidenceScore": confidence_score,
            "protein": protein,
            "energy": energy,
            "child_id": child_id,
            "breastfeeding": "Yes" if breastfeeding else "No",
        }

        return jsonify(response_data)

    except Exception as e:
        return jsonify({"error": str(e)}), 500

def get_all_predictions():
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            """
            SELECT sp.predict_id, sp.child_weight, sp.child_height, sp.predict_result, sp.child_id, 
                   sp.created_at, sp.updated_at, sp.confidenceScore, c.breastfeeding
            FROM StuntPredict sp
            JOIN Child c ON sp.child_id = c.child_id
        """
        )
        predictions = cursor.fetchall()
        conn.close()

        if not predictions:
            return (
                jsonify(
                    {"status": True, "message": "No predictions found.", "data": []}
                ),
                200,
            )

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

def get_prediction_by_id(predict_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            """
            SELECT sp.predict_id, sp.child_weight, sp.child_height, sp.predict_result, sp.child_id, 
                   sp.created_at, sp.updated_at, sp.confidenceScore, c.breastfeeding
            FROM StuntPredict sp
            JOIN Child c ON sp.child_id = c.child_id
            WHERE sp.predict_id = %s
        """,
            (predict_id,),
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

def get_predictions_by_child(child_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            """
            SELECT sp.predict_id, sp.child_weight, sp.child_height, sp.predict_result, sp.child_id, 
                   sp.created_at, sp.updated_at, sp.confidenceScore, c.breastfeeding
            FROM StuntPredict sp
            JOIN Child c ON sp.child_id = c.child_id
            WHERE sp.child_id = %s
        """,
            (child_id,),
        )
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

def post_notes(predict_id):
    try:
        # Parse request data
        data = request.json
        note = data.get("note")

        # Connect to MySQL and update the note in StuntPredict table
        conn = get_mysql_connection()
        cursor = conn.cursor()

        cursor.execute(
            "UPDATE StuntPredict SET note = %s WHERE predict_id = %s",
            (note, predict_id),
        )

        conn.commit()
        
        # Fetch updated note for response
        cursor.execute(
            "SELECT note FROM StuntPredict WHERE predict_id = %s",
            (predict_id,)
        )
        updated_note = cursor.fetchone()[0]

        cursor.close()
        conn.close()

        return jsonify({"message": "Note added successfully.", "note": updated_note}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500
