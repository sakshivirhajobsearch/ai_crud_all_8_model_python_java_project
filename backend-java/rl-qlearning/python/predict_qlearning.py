# predict_qlearning.py

import sys
import numpy as np
from grid_env import GridEnv

rows, cols = 5, 5
num_actions = 4
MAX_STEPS = 50  # prevent infinite loop

# Load Q-table
q_table = np.load("q_table.npy")

# Initialize environment
env = GridEnv(rows, cols)

# Get start coordinates (default 0,0)
x, y = (int(sys.argv[1]), int(sys.argv[2])) if len(sys.argv) >= 3 else (0, 0)
state = env.reset(x, y)

def state_to_index(state):
    return state[0] * cols + state[1]

state_index = state_to_index(state)
done = False
step_count = 0

print(f"Starting Q-learning prediction from position: ({x},{y})\n")

while not done and step_count < MAX_STEPS:
    action = np.argmax(q_table[state_index])
    next_state, reward, done = env.step(action)
    state_index = state_to_index(next_state)
    step_count += 1

    env.render()
    print(f"Step: {step_count}, Action: {action}, Reward: {reward}\n")

if done:
    print(f"Prediction finished in {step_count} steps!")
else:
    print(f"Prediction stopped after {MAX_STEPS} steps. Agent may not have reached the goal.")
