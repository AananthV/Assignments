import numpy as np
from tqdm import tqdm # For showing training progress

class Layer:

    def __init__(self, num_inputs, num_outputs, activation, learning_rate):

        self.num_inputs = num_inputs
        self.num_outputs = num_outputs
        self.activation = activation
        self.learning_rate = learning_rate

        # Initialise the wrights and biases
        self.weights = np.random.rand(num_inputs, num_outputs)
        self.biases = np.zeros(num_outputs)

    def forward(self, previous):

        # Calculate Dense values
        self.pre_activation = np.matmul(previous, self.weights) + self.biases

        # Activate the neurons
        self.activated = self.activation['function'](self.pre_activation)

        return self.activated

    def backward(self, prev_layer, prev_activations, deltas):
        # Calculate the deltas of the next layer
        n_deltas = self.activation['gradient'](prev_layer) * np.matmul(deltas, self.weights.T)

        # Update weights and biases
        self.weights -= (self.learning_rate * np.matmul(prev_activations.T, deltas))
        self.biases -= (self.learning_rate * np.sum(deltas, axis = 0))

        return n_deltas

class NeuralNetwork:

    def __init__(self, layers):

        self.layers = []

        for layer in layers:

            # Create and add layers
            self.layers.append(
                Layer(layer['inputs'], layer['outputs'], layer['activation'], layer['learning_rate'])
            )

    def train(self, X, Y, num_epochs = 1):

        print("Training...")

        for epoch in tqdm(range(num_epochs)):

            for _input, _target in zip(X, Y):

                # Set input
                next_input = _input[np.newaxis]

                # Forward propogation
                for layer in self.layers:

                    next_input = layer.forward(next_input)

                # Set delta of last layer to Y[i] - prediction
                next_deltas = self.layers[-1].activation['gradient'](self.layers[-1].pre_activation) * (next_input - _target[np.newaxis])

                # backpropogation to train the network
                for l in range(len(self.layers)):
                    pre_activation = self.layers[len(self.layers) - l - 2].pre_activation
                    activated = self.layers[len(self.layers) - l - 2].activated

                    if (l == len(self.layers) - 1):
                        pre_activation = activated = _input[np.newaxis]
                    
                    next_deltas = self.layers[len(self.layers) - l - 1].backward(
                        pre_activation,
                        activated,
                        next_deltas
                    )

    def predict(self, X):

        predictions = []

        for x in X:
            next_input = x[np.newaxis]

            # Forward propogate and find the output
            for layer in self.layers:

                    next_input = layer.forward(next_input)

            # Required output is the last layer's activations
            predictions.append(next_input)
        
        return np.array(predictions)