from transformers import pipeline

# Explicitly specify the model and use CPU
sentiment_pipeline = pipeline(
    "sentiment-analysis",
    model="distilbert-base-uncased-finetuned-sst-2-english",
    device=-1  # -1 means CPU
)

# Example usage
text = "I love using ChatGPT!"
result = sentiment_pipeline(text)
print(result)
