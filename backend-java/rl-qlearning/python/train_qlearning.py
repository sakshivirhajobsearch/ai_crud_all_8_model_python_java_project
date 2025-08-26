# train_qlearning.py

import numpy as np
from grid_env import GridEnv

# Parameters
rows, cols = 5, 5
num_actions = 4
alpha = 0.1      # learning rate
gamma = 0.9      # discount factor
epsilon = 0.1    # exploration rate
episodes = 500

# Initialize Q-table
q_table = np.zeros((rows * cols, num_actions))

# Helper: convert (x,y) to state index
def state_to_index(state):
    x, y = state
    return x * cols + y

# Initialize environment
env = GridEnv(rows, cols)

for ep in range(episodes):
    state = env.reset()
    state_index = state_to_index(state)
    done = False
    while not done:
        # epsilon-greedy action
        if np.random.rand() < epsilon:
            action = np.random.randint(num_actions)
        else:
            action = np.argmax(q_table[state_index])

        next_state, reward, done = env.step(action)
        next_index = state_to_index(next_state)

        # Q-learning update
        q_table[state_index, action] = q_table[state_index, action] + \
            alpha * (reward + gamma * np.max(q_table[next_index]) - q_table[state_index, action])

        state_index = next_index

# Save Q-table
np.save("q_table.npy", q_table)
print("Training done and Q-table saved.")
