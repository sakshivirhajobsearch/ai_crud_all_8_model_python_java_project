# grid_env.py

class GridEnv:
    def __init__(self, rows=5, cols=5):
        self.rows = rows
        self.cols = cols
        self.goal = (rows - 1, cols - 1)
        self.state = (0, 0)
        self.num_actions = 4  # up, right, down, left

    def reset(self, x=0, y=0):
        self.state = (x, y)
        return self.state

    def step(self, action):
        x, y = self.state
        if action == 0 and x > 0:  # up
            x -= 1
        elif action == 1 and y < self.cols - 1:  # right
            y += 1
        elif action == 2 and x < self.rows - 1:  # down
            x += 1
        elif action == 3 and y > 0:  # left
            y -= 1
        self.state = (x, y)
        reward = 1 if self.state == self.goal else 0
        done = self.state == self.goal
        return self.state, reward, done

    def render(self):
        grid = [['.' for _ in range(self.cols)] for _ in range(self.rows)]
        x, y = self.state
        gx, gy = self.goal
        grid[gx][gy] = 'G'
        grid[x][y] = 'A'
        for row in grid:
            print(' '.join(row))
        print()
