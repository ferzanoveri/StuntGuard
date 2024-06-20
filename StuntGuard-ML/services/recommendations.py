from flask import request, jsonify
import numpy as np
import os
from db import *
from nanoid import generate
import pandas as pd
import pickle

# Load preprocessed data from CSV
data_path = "data/Food_Dataset.csv"
food_df_new = pd.read_csv(data_path)
food_df = food_df_new.drop(['Jumlah Porsi', 'Takaran Porsi'], axis=1)
food_df = food_df.loc[:, ['Protein (g)', 'Kalori (kal)']]

current_dir = os.path.dirname(os.path.abspath(__file__))
# Path relatif ke file model h5
model_path = os.path.join(current_dir,"RecommendationModel.pkl")
# Load the model using pickle
with open(model_path, 'rb') as file:
    model = pickle.load(file)

scaler_path = os.path.join(current_dir,"scaler.pkl")
# Load the scaler using pickle
with open(scaler_path, 'rb') as file:
    scaler = pickle.load(file)

def check_model_attributes():
    try:
        # Check model attributes
        model_attributes = dir(scaler)
        return jsonify({"scaler_attributes": model_attributes})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

def recommend_foods_greedy_knn(child, food_df, model, scaler, n_recommendations):
    energy = child['Energy_kal']
    protein = child['Protein_g']
    remaining_energy = energy
    remaining_protein = protein
    recommendations = []
    percentages = [0.4, 0.5, 0.5, 0.5, 1.0]

    for pct in percentages:
        if remaining_energy <= 0 and remaining_protein <= 0:
            break

        target_energy = remaining_energy * pct
        target_protein = remaining_protein * pct
        input_data = np.array([[target_protein, target_energy]])
        input_data_scaled = scaler.transform(input_data)
        indices = model.kneighbors(input_data_scaled, n_neighbors=5, return_distance=False)

        best_food = None
        best_diff = float('inf')

        for idx in indices[0]:
            if idx >= len(food_df):
                continue  # Skip if index is out-of-bounds

            food_item = food_df.iloc[idx]
            energy_diff = abs(food_item['Kalori (kal)'] - target_energy)
            protein_diff = abs(food_item['Protein (g)'] - target_protein)
            total_diff = energy_diff + protein_diff

            if total_diff < best_diff:
                best_diff = total_diff
                best_food = food_item

        if best_food is not None:
            recommendations.append(best_food.to_dict())
            remaining_energy -= best_food['Kalori (kal)']
            remaining_protein -= best_food['Protein (g)']
            food_df = food_df.drop(best_food.name)

    return recommendations
def post_recom(predict_id):
    try:
        data = request.get_json()
        child_id = data.get('child_id')
        
        connection = get_mysql_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute("""
            SELECT c.child_id, sp.energy AS Energy_kal, sp.protein AS Protein_g
            FROM Child c
            JOIN StuntPredict sp ON c.child_id = sp.child_id
            WHERE sp.predict_id = %s
        """, (predict_id,))
        child_data = cursor.fetchone()

        print(child_data)
        
        if not child_data:
            cursor.close()
            connection.close()
            return jsonify({'error': 'Child data not found'}), 404
        
        energy_kal = float(child_data['Energy_kal'])
        protein_g = float(child_data['Protein_g'])
        child_id = child_data['child_id']
        print('energy dan protein:', energy_kal, protein_g)

        # Check if there's already a recommendation for this predict_id
        cursor.execute("""
            SELECT recommendation_id FROM Recommendation
            WHERE predict_id = %s
        """, (predict_id,))
        recommendation = cursor.fetchone()

        if recommendation:
            # Use the existing recommendation_id
            recommendation_id = recommendation['recommendation_id']
            
            # Delete existing food details for this recommendation_id
            cursor.execute("""
                DELETE FROM FoodDetails WHERE recommendation_id = %s
            """, (recommendation_id,))
            connection.commit()
        else:
            recommendation_id = predict_id
            
            # Insert new recommendation entry
            cursor.execute("""
                INSERT INTO Recommendation (
                    recommendation_id, child_id, predict_id, created_at, updated_at
                ) VALUES (%s, %s, %s, NOW(), NOW())
            """, (recommendation_id, child_id, predict_id))
            connection.commit()

        # Use the model to scale and find nearest neighbors
        input_data = np.array([[protein_g, energy_kal]])
        input_data_scaled = scaler.transform(input_data)
        print("Input data (scaled):", input_data_scaled)

        # Process recommendations based on nearest neighbors
        recommended_foods = recommend_foods_greedy_knn(child_data, food_df_new, model, scaler, 5)

        food_details = []

        for food_data in recommended_foods:
            food_details.append({
                'food_id': generate(size=8),
                'food_name': food_data['Nama'],
                'category': food_data['Kategori'],
                'portion': int(food_data['Jumlah Porsi']),
                'unit': food_data['Takaran Porsi'],
                'calories': float(food_data['Kalori (kal)']),
                'fat': float(food_data['Lemak (g)']),
                'saturated_fat': float(food_data['Lemak Jenuh (g)']),
                'polyunsaturated_fat': float(food_data['Lemak Tak Jenuh Ganda (g)']),
                'monounsaturated_fat': float(food_data['Lemak Tak Jenuh Tunggal (g)']),
                'cholesterol': float(food_data['Kolestrol (g)']),
                'protein': float(food_data['Protein (g)']),
                'carbohydrates': float(food_data['Karbohidrat (g)']),
                'fiber': float(food_data['Serat (g)']),
                'sugar': float(food_data['Gula (g)']),
                'sodium': float(food_data['Sodium (g)']),
                'potassium': float(food_data['Kalium (g)'])
            })
        
        print('Recommended foods:', food_details)

        # Insert new food details into FoodDetails table
        for food in food_details:
            cursor.execute("""
                INSERT INTO FoodDetails (
                    food_id, food_name, category, portion, unit, calories, fat, saturated_fat,
                    polyunsaturated_fat, monounsaturated_fat, cholesterol, protein, carbohydrates,
                    fiber, sugar, sodium, potassium, recommendation_id, created_at
                ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, NOW())
            """, (
                food['food_id'], food['food_name'], food['category'], food['portion'], food['unit'],
                food['calories'], food['fat'], food['saturated_fat'], food['polyunsaturated_fat'],
                food['monounsaturated_fat'], food['cholesterol'], food['protein'], food['carbohydrates'],
                food['fiber'], food['sugar'], food['sodium'], food['potassium'], recommendation_id
            ))
        connection.commit()

        # Retrieve the recommendation and food details to return in the response
        cursor.execute("""
            SELECT * FROM Recommendation WHERE recommendation_id = %s
        """, (recommendation_id,))
        recommendation_data = cursor.fetchone()

        cursor.execute("""
            SELECT * FROM FoodDetails WHERE recommendation_id = %s
        """, (recommendation_id,))
        food_details_data = cursor.fetchall()

        cursor.close()
        connection.close()

        # Calculate total recommended calories and protein
        recommended_foods_df = pd.DataFrame(recommended_foods)
        total_energy = recommended_foods_df['Kalori (kal)'].sum()
        total_protein = recommended_foods_df['Protein (g)'].sum()

        response_data = {
            'recommendation': recommendation_data,
            'food_details': food_details_data,
            'total_energy': total_energy,
            'total_protein': total_protein
        }

        return jsonify(response_data)
    except Exception as e:
            return jsonify({"error": str(e)}), 500

# Get all recommendations 
def get_all_recommendations():
    try:
        connection = get_mysql_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute("""
            SELECT * FROM Recommendation
            ORDER BY created_at DESC
        """)
        recommendations = cursor.fetchall()
        cursor.close()
        connection.close()

        if not recommendations:
            return jsonify({"status": True, "message": "No recommendations found.", "data": []}), 200

        return jsonify({'recommendations': recommendations})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Get all recommendations by Child
def get_recommendations_by_child(child_id):
    try:
        connection = get_mysql_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute("""
            SELECT * FROM Recommendation
            WHERE child_id = %s
            ORDER BY created_at DESC
        """, (child_id,))
        recommendations = cursor.fetchall()
        cursor.close()
        connection.close()

        if not recommendations:
            return jsonify({"error": "Recommendation not found."}), 404

    except Exception as e:
        return jsonify({"error": str(e)}), 500

    return jsonify({'recommendations': recommendations})

# Get all food details in recoommendation
def get_food_details_by_recommendation(recommendation_id):
    try:
        connection = get_mysql_connection()
        cursor = connection.cursor(dictionary=True)
        cursor.execute("""
            SELECT * FROM FoodDetails
            WHERE recommendation_id = %s
        """, (recommendation_id,))
        food_details = cursor.fetchall()
        cursor.close()
        connection.close()

        if not food_details:
            return jsonify({"error": "No food details found for this recommendation."}), 404

        return jsonify({'food_details': food_details})
    except Exception as e:
            return jsonify({"error": str(e)}), 500