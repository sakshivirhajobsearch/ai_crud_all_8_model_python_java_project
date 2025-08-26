from flask import Flask, request, jsonify

app = Flask(__name__)

# Root route for browser check
@app.route('/')
def home():
    return "AlphaFold Dummy API is running!"

# /predict route for POST (and optional GET for testing)
@app.route('/predict', methods=['POST', 'GET'])
def predict():
    if request.method == 'POST':
        data = request.json
        sequence = data.get("sequence", "")
        result = {"predicted_structure": "dummy_structure_for_" + sequence}
        return jsonify(result)
    else:  # GET request fallback for quick browser test
        return "Use POST method with JSON: {\"sequence\": \"YOUR_PROTEIN_SEQUENCE\"}"

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5005)
