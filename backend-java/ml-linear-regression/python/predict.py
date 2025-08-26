from flask import Flask, request, jsonify
import joblib
import os

app = Flask(__name__)

# Load model safely
model_path = os.path.join(os.path.dirname(__file__), 'model.pkl')
if not os.path.exists(model_path):
    raise FileNotFoundError(f"Model file not found at {model_path}. Please run train_model.py first.")

model = joblib.load(model_path)

# Home route
@app.route('/')
def home():
    return "ML Linear Regression API is running!"

# Prediction route (GET for browser test, POST for actual prediction)
@app.route('/predict', methods=['GET', 'POST'])
def predict():
    if request.method == 'GET':
        return "Send a POST request with JSON body containing 'features'. Example: {\"features\": [5.1, 3.5, 1.4, 0.2]}"
    
    # POST request for actual prediction
    try:
        data = request.get_json()
        if not data or 'features' not in data:
            return jsonify({"error": "Missing 'features' in JSON body"}), 400

        features = data['features']
        if not isinstance(features, list):
            return jsonify({"error": "'features' should be a list"}), 400

        prediction = model.predict([features])
        return jsonify({'prediction': prediction.tolist()})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
