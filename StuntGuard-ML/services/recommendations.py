from flask import Flask, request, jsonify
import numpy as np
import mysql.connector
import os
from datetime import datetime, date
from dotenv import load_dotenv
import tensorflow as tf
from db import *
from nanoid import generate
import pandas as pd

app = Flask(__name__)

current_dir = os.path.dirname(os.path.abspath(__file__))
model_path = os.path.join(current_dir, "recommender_model.h5")

model = tf.keras.models.load_model(model_path)
print(type(model))
model.summary()
# Load preprocessed data from CSV
data_path = ("data/preprocessed_dataset.csv")
df = pd.read_csv(data_path)

def check_model_attributes():
    try:
        # Check model attributes
        model_attributes = dir(model)
        return jsonify({"model_attributes": model_attributes})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

def post_recom(predict_id):
    data = request.get_json()
    random_seed = np.random.randint(1000)
    np.random.seed(random_seed)
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
    
    if not child_data:
        cursor.close()
        connection.close()
        return jsonify({'error': 'Child data not found'}), 404
    
    energy_kal = float(child_data['Energy_kal'])
    protein_g = float(child_data['Protein_g'])
    # energy_kal = 479.6328
    # protein_g = 17.98623
    child_id = child_data['child_id']
    print('energy dan protein:', energy_kal, protein_g)

    # Check if there's already a recommendation for today
    today_date = date.today()
    cursor.execute("""
        SELECT recommendation_id FROM Recommendation
        WHERE child_id = %s AND DATE(created_at) = %s
    """, (child_id, today_date))
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

    input_data = np.array([[protein_g, energy_kal]])
    print('input: ', input_data)

    predictions = model.predict(input_data)
    print('predictions: ', predictions)

    # Extracting the top 5 recommendations
    recommendation_indices = np.argsort(predictions[0])[-5:]
    print('recommendation_indices', recommendation_indices)

    print(df.columns)
    recommended_foods = []
    for idx in recommendation_indices:
        food_data = df.iloc[idx]
        
        # Convert numeric values to appropriate data types
        portion = int(food_data['Jumlah Porsi'])
        calories = float(food_data['Kalori (kkal)'])
        fat = float(food_data['Lemak (g)'])
        saturated_fat = float(food_data['Lemak Jenuh (g)'])
        polyunsaturated_fat = float(food_data['Lemak Tak Jenuh Ganda (g)'])
        monounsaturated_fat = float(food_data['Lemak Tak Jenuh Tunggal (g)'])
        cholesterol = float(food_data['Kolestrol (g)'])
        protein = float(food_data['Protein (g)'])
        carbohydrates = float(food_data['Karbohidrat (g)'])
        fiber = float(food_data['Serat (g)'])
        sugar = float(food_data['Gula (g)'])
        sodium = float(food_data['Sodium (g)'])
        potassium = float(food_data['Kalium (g)'])


        recommended_foods.append({
            'food_id': generate(size=8),
            'food_name': food_data['Nama'],
            'category': food_data['Kategori'],
            'portion': portion,
            'unit': food_data['Takaran Porsi'],
            'calories': calories,
            'fat': fat,
            'saturated_fat': saturated_fat,
            'polyunsaturated_fat': polyunsaturated_fat,
            'monounsaturated_fat': monounsaturated_fat,
            'cholesterol': cholesterol,
            'protein': protein,
            'carbohydrates': carbohydrates,
            'fiber': fiber,
            'sugar': sugar,
            'sodium': sodium,
            'potassium': potassium
        })
    
    print('recommended_foods',recommended_foods)

     # Insert new food details into FoodDetails table
    for food in recommended_foods:
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

    response_data = {
        'recommendation': recommendation_data,
        'food_details': food_details_data
    }

    return jsonify(response_data)

# Get all recommendations 
def get_all_recommendations():
    connection = get_mysql_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("""
        SELECT * FROM Recommendation
    """)
    recommendations = cursor.fetchall()
    cursor.close()
    connection.close()

    return jsonify({'recommendations': recommendations})

# Get all recommendations by Child
def get_recommendations_by_child(child_id):
    connection = get_mysql_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("""
        SELECT * FROM Recommendation
        WHERE child_id = %s
    """, (child_id,))
    recommendations = cursor.fetchall()
    cursor.close()
    connection.close()

    return jsonify({'recommendations': recommendations})

# Get all food details in recoommendation
def get_food_details_by_recommendation(recommendation_id):
    connection = get_mysql_connection()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("""
        SELECT * FROM FoodDetails
        WHERE recommendation_id = %s
    """, (recommendation_id,))
    food_details = cursor.fetchall()
    cursor.close()
    connection.close()

    return jsonify({'food_details': food_details})
