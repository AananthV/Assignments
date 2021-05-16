import numpy as np
from tqdm import tqdm # For showing training progress

class CPNN:
    def __init__(self, num_inputs, alpha = 1, beta = 0, gamma = 1):
        self.alpha = alpha
        self.beta = beta
        self.gamma = gamma

        self.num_inputs = num_inputs

        # Number of outputs of Kohenen layer
        self.num_kohenen_outputs = num_inputs

        # Initialise the weights randomly
        self.kohenen_weights = np.random.rand(self.num_kohenen_outputs, self.num_inputs)
        self.grossberg_weights = np.random.rand(self.num_inputs, self.num_inputs)

    def find_kohenen_winner(self, x):
        # Calculate euclidean distance
        dist = np.sqrt(np.sum((self.kohenen_weights - x) ** 2, axis=0))

        # Find winner
        winner = np.argmin(dist)

        return winner

    def update_kohenen_weights(self, x):
        # Get winner node
        winner = self.find_kohenen_winner(x)

        # Update weights
        update_vector = np.ones((self.num_kohenen_outputs)) * self.beta
        update_vector[winner] = self.alpha

        self.kohenen_weights += (x - self.kohenen_weights) * update_vector

        self.alpha *= 0.5
        self.beta *= 0.5

    def update_grossberg_weights(self, x):
        # Get kohenen winner node
        winner = self.find_kohenen_winner(x)
        
        self.grossberg_weights[winner] += self.gamma * np.dot(x, (x - self.grossberg_weights[winner]))

        self.gamma *= 0.5

    def update_weights(self, x):
        self.update_kohenen_weights(x)
        self.update_grossberg_weights(x)

    def get_kohenen_output(self, x):
        # Get winner node
        winner = self.find_kohenen_winner(x)

        # Get output
        output = np.zeros((self.num_kohenen_outputs))
        output[winner] = 1

        return output

    def forward(self, x):
        # # Get winner node
        # winner = self.find_kohenen_winner(x)

        output = np.matmul(self.grossberg_weights, x)

        return output

    def train(self, X, Y, num_epochs=20):

        # Train kohenen network. Phase 1
        print("Phase 1")
        for _ in tqdm(range(num_epochs)):
            for i in range(len(X)):
                self.update_kohenen_weights([*X[i], *Y[i]])

        # Train grossberg network. Phase 2
        print("Phase 2")
        for _ in tqdm(range(num_epochs)):
            for i in range(len(X)):
                self.update_grossberg_weights([*X[i], *Y[i]])

    def predict(self, X):
        predictions = []
        for i in range(len(X)):
            output = self.forward([*X[i], 0, 0, 0])[-3:]

            predictions.append(output)

        return predictions
        
