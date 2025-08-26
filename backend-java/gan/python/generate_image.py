# gan/python/generate_image.py
import torch
from train_gan import Generator, latent_dim, image_size
import matplotlib.pyplot as plt
import sys
import os

os.makedirs("gan/images/generated", exist_ok=True)

def generate(index):
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    generator = Generator().to(device)
    generator.load_state_dict(torch.load("gan/generator.pth", map_location=device))
    generator.eval()

    z = torch.randn(1, latent_dim).to(device)
    with torch.no_grad():
        fake = generator(z).cpu().view(28, 28)

    filename = f"gan/images/generated/fake_digit_{index}.png"
    plt.imsave(filename, fake.numpy(), cmap="gray")
    print(f"✅ Image saved to: {filename}")

if __name__ == "__main__":
    index = int(sys.argv[1]) if len(sys.argv) > 1 else 0
    generate(index)
