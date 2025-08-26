import torch
from PIL import Image

image_path = "zidane.jpg"  # or use full path here

try:
    model = torch.hub.load('ultralytics/yolov5', 'yolov5s', pretrained=True)
    results = model(image_path)
    results.print()
    results.save()
except FileNotFoundError:
    print("[ERROR] File not found:", image_path)
