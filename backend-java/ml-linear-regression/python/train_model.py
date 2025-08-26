# python/train_model.py
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
import joblib

# Sample training data (replace with CSV if needed)
data = {
    'area': [1000, 1500, 1800, 2400, 3000],
    'price': [300000, 400000, 500000, 600000, 650000]
}

df = pd.DataFrame(data)

X = df[['area']]
y = df['price']

model = LinearRegression()
model.fit(X, y)

joblib.dump(model, 'model.pkl')
print("Model trained and saved to model.pkl")
