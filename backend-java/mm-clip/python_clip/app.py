from flask import Flask, request, jsonify
from clip_inference import CLIPModel

app = Flask(__name__)
model = CLIPModel()

@app.route("/", methods=["GET"])
def index():
    return "CLIP Model API is running. Use POST /predict"

@app.route("/predict", methods=["POST"])
def predict():
    image = request.files["image"]
    text = request.form["text"]
    similarity = model.predict_similarity(image, text)
    return jsonify({"similarity": similarity})

if __name__ == "__main__":
    app.run(debug=True)
