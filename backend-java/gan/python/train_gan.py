# gan/python/train_gan.py
import torch
from torch import nn
from torchvision import datasets, transforms
from torch.utils.data import DataLoader
from torchvision.utils import save_image
import os

# -----------------------
# Hyperparameters
# -----------------------
image_size = 28
latent_dim = 100
batch_size = 32       # smaller batch for CPU; increase if GPU
epochs = 5            # smaller for quick testing; increase if GPU
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
os.makedirs("gan/images/generated", exist_ok=True)

# -----------------------
# DataLoader
# -----------------------
transform = transforms.Compose([
    transforms.ToTensor(),
    transforms.Normalize([0.5], [0.5])
])
dataloader = DataLoader(
    datasets.MNIST('.', train=True, download=True, transform=transform),
    batch_size=batch_size, shuffle=True
)

# -----------------------
# Generator
# -----------------------
class Generator(nn.Module):
    def __init__(self):
        super().__init__()
        self.main = nn.Sequential(
            nn.Linear(latent_dim, 256),
            nn.ReLU(True),
            nn.Linear(256, 512),
            nn.ReLU(True),
            nn.Linear(512, image_size * image_size),
            nn.Tanh()
        )

    def forward(self, x):
        return self.main(x).view(-1, 1, image_size, image_size)

# -----------------------
# Discriminator
# -----------------------
class Discriminator(nn.Module):
    def __init__(self):
        super().__init__()
        self.main = nn.Sequential(
            nn.Flatten(),
            nn.Linear(image_size * image_size, 512),
            nn.LeakyReLU(0.2),
            nn.Linear(512, 1),
            nn.Sigmoid()
        )

    def forward(self, x):
        return self.main(x)

# -----------------------
# Model, Loss, Optimizer
# -----------------------
generator = Generator().to(device)
discriminator = Discriminator().to(device)

criterion = nn.BCELoss()
optimizer_g = torch.optim.Adam(generator.parameters(), lr=0.0002)
optimizer_d = torch.optim.Adam(discriminator.parameters(), lr=0.0002)

# -----------------------
# Training Loop
# -----------------------
try:
    for epoch in range(epochs):
        for i, (real, _) in enumerate(dataloader):
            real = real.to(device)
            b_size = real.size(0)
            real_labels = torch.ones(b_size, 1).to(device)
            fake_labels = torch.zeros(b_size, 1).to(device)

            # ---- Train Generator ----
            z = torch.randn(b_size, latent_dim).to(device)
            fake = generator(z)
            output = discriminator(fake)
            loss_g = criterion(output, real_labels)

            optimizer_g.zero_grad()
            loss_g.backward()
            optimizer_g.step()

            # ---- Train Discriminator ----
            output_real = discriminator(real)
            loss_real = criterion(output_real, real_labels)
            output_fake = discriminator(fake.detach())
            loss_fake = criterion(output_fake, fake_labels)
            loss_d = loss_real + loss_fake

            optimizer_d.zero_grad()
            loss_d.backward()
            optimizer_d.step()

            # ---- Print Progress ----
            if i % 100 == 0:
                print(f"Epoch [{epoch+1}/{epochs}] Batch [{i}/{len(dataloader)}] "
                      f"Loss D: {loss_d.item():.4f}, Loss G: {loss_g.item():.4f}")

            # ---- Save Sample Images ----
            if i % 500 == 0:
                save_image(fake.data[:25], f"gan/images/generated/fake_epoch{epoch+1}_batch{i}.png",
                           nrow=5, normalize=True)

except KeyboardInterrupt:
    print("⚠️ Training interrupted. Saving models...")

finally:
    torch.save(generator.state_dict(), "gan/generator.pth")
    torch.save(discriminator.state_dict(), "gan/discriminator.pth")
    print("✅ DCGAN models saved.")
