import os
import pickle
import numpy as np
from flask import Flask, request, jsonify
from nanoid import generate
from datetime import datetime
from db import *
import pandas as pd

current_dir = os.path.dirname(os.path.abspath(__file__))
# Path relatif ke file model h5
model_path = os.path.join(current_dir,"random_forest_model.pkl")
# Load the model using pickle
with open(model_path, 'rb') as file:
    model = pickle.load(file)

def check_model_attributes():
    try:
        model_attributes = dir(model)
        print(type(model))   
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
    "breastfeeding",
    "energy",
    "protein"
]

def label_predictions(predictions):
    return [
        {PREDICTION_LABELS[i]: value for i, value in enumerate(prediction)}
        for prediction in predictions
    ]

def post_predict(child_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM Child WHERE child_id = %s", (child_id,))
        child_data = cursor.fetchone()
        conn.close()

        if not child_data:
            return jsonify({"error": "Child data not found."}), 404

        data = None
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
        elif request.headers['Content-Type'] == 'application/x-www-form-urlencoded':
            data = request.form
        
        if not data:
            return jsonify({"error": "Invalid content type."}), 400


        # Proses data berdasarkan jenis konten
        child_weight = float(data.get("child_weight"))
        child_height = float(data.get("child_height"))

        input_breastfeeding = data.get("breastfeeding")
        if input_breastfeeding == "Yes":
            breastfeeding = True
        elif input_breastfeeding == "No":
            breastfeeding = False
        else:
            breastfeeding = bool(child_data[8])  # Use default value from Child data

        prediction_data = {
            "Gender": child_data[3],
            "Age": child_data[5],
            "Birth Weight": child_data[6],
            "Birth Length": child_data[7],
            "Body Weight": child_weight,
            "Body Length": child_height,
            "Breastfeeding": 1 if breastfeeding else 0,
        }

        # prediction_array = np.array([list(prediction_data.values())])
        prediction_df = pd.DataFrame([prediction_data])
        if not hasattr(model, 'predict'):
                    return jsonify({"error": "Loaded object is not a model with predict method."}), 500

        print(prediction_df)
        prediction = model.predict(prediction_df)
        print(prediction)
        predict_result = bool(prediction[0])
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

        predict_id = generate(size=8)
        created_at = datetime.now().strftime("%Y-%m-%d")
        updated_at = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

        conn = get_mysql_connection()
        cursor = conn.cursor()

        cursor.execute(
            "SELECT * FROM StuntPredict WHERE DATE(created_at) = %s AND child_id = %s", (created_at, child_id)
        )
        existing_prediction = cursor.fetchone()
        if existing_prediction:
            predict_id = existing_prediction[0]
            cursor.execute(
                "UPDATE StuntPredict SET child_weight = %s, child_height = %s, predict_result = %s, protein = %s, energy = %s, updated_at = %s WHERE predict_id = %s AND child_id = %s",
                (
                    child_weight,
                    child_height,
                    predict_result,
                    protein,
                    energy,
                    updated_at,
                    predict_id,
                    child_id,
                ),
            )
        else:
            cursor.execute(
                "INSERT INTO StuntPredict (predict_id, child_weight, child_height, predict_result, child_id, created_at, protein, energy, updated_at) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)",
                (
                    predict_id,
                    child_weight,
                    child_height,
                    predict_result,
                    child_id,
                    created_at,
                    protein,
                    energy,
                    updated_at,
                ),
            )

        cursor.execute(
            "UPDATE Child SET breastfeeding = %s WHERE child_id = %s",
            (breastfeeding, child_id),
        )

        conn.commit()
        cursor.close()
        conn.close()

        response_data = {
            "predict_id": predict_id,
            "child_weight": child_weight,
            "child_height": child_height,
            "predict_result": "Yes" if predict_result else "No",
            "created_at": created_at,
            "updated_at": updated_at,
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
                   sp.created_at, sp.updated_at, c.breastfeeding, sp.energy, sp.protein
            FROM StuntPredict sp
            JOIN Child c ON sp.child_id = c.child_id
        """
        )
        predictions = cursor.fetchall()
        conn.close()

        if not predictions:
            return jsonify({"status": True, "message": "No predictions found.", "data": []}), 200

        labeled_predictions = label_predictions(predictions)

        return jsonify({
            "status": True,
            "message": "All predictions retrieved successfully.",
            "data": labeled_predictions,
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

def get_prediction_by_id(predict_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            """
            SELECT sp.predict_id, sp.child_weight, sp.child_height, sp.predict_result, sp.child_id, 
                   sp.created_at, sp.updated_at, c.breastfeeding, sp.energy, sp.protein
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

        return jsonify({
            "status": True,
            "message": "Prediction retrieved successfully.",
            "data": labeled_prediction,
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

def get_predictions_by_child(child_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()
        cursor.execute(
            """
            SELECT sp.predict_id, sp.child_weight, sp.child_height, sp.predict_result, sp.child_id, 
                   sp.created_at, sp.updated_at, c.breastfeeding, sp.energy, sp.protein
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

        return jsonify({
            "status": True,
            "message": "All predictions for the child retrieved successfully.",
            "data": labeled_predictions,
        }), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

def post_notes(predict_id):
    try:
        data = None
        if request.headers['Content-Type'] == 'application/json':
            data = request.json
        elif request.headers['Content-Type'] == 'application/x-www-form-urlencoded':
            data = request.form

        if not data:
            return jsonify({"error": "Invalid content type."}), 400
        
        note = data.get("note")

        conn = get_mysql_connection()
        cursor = conn.cursor()

        cursor.execute(
            "UPDATE StuntPredict SET note = %s WHERE predict_id = %s",
            (note, predict_id),
        )

        conn.commit()
        
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

def get_notes(predict_id):
    try:
        conn = get_mysql_connection()
        cursor = conn.cursor()

        cursor.execute(
            "SELECT note FROM StuntPredict WHERE predict_id = %s",
            (predict_id,)
        )
        note = cursor.fetchone()
        
        cursor.close()
        conn.close()

        if note:
            return jsonify({"note": note[0]}), 200
        else:
            return jsonify({"message": "Note not found."}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 500
