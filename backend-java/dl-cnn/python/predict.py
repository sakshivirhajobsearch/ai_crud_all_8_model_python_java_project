# python/predict.py
import sys
import tensorflow as tf
import numpy as np
from tensorflow.keras.preprocessing import image

model = tf.keras.models.load_model('cnn_model.h5')

def predict_image(img_path):
    img = image.load_img(img_path, target_size=(64, 64))
    img_array = image.img_to_array(img) / 255.0
    img_array = np.expand_dims(img_array, axis=0)
    prediction = model.predict(img_array)[0][0]
    return 'Dog' if prediction > 0.5 else 'Cat'

if __name__ == '__main__':
    img_path = sys.argv[1]
    print(predict_image(img_path))
