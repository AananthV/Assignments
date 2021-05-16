## Authentication of Bank Notes using a Genetic Algorithm

### Introduction

In this project, we have used a genetic algorithm to optimize the weights of a Feed Forward Neural Network. This neural network is used to classify bank notes as **Authentic** or **Inauthentic**. This project was done by **V. Aananth (106118103)** and **Madhav Aggarwal (106118053)** as an assignment for **Soft Computing (CSHO17)**.

### Dataset

The [Bank Note Authentication Dataset](http://archive.ics.uci.edu/ml/datasets/banknote+authentication) was used to train our model. The dataset contains 1,372 observations with 4 input variables and 1 output variable. The dataset has an equal distribution of Authentic and Inauthentic notes. The variable names are as follows:

1.  Variance of Wavelet Transformed image (continuous).
2.  Skewness of Wavelet Transformed image (continuous).
3.  Kurtosis of Wavelet Transformed image (continuous).
4.  Entropy of image (continuous).
5.  Class (0 for authentic, 1 for inauthentic).

### Implementation

#### Neural Network

A Feed Forward Neural Network with two hidden layers was chosen to perform the classification.

![enter image description here](https://i.imgur.com/5wLgmbJ.png)

The output is a value between 0 and 1. Let ***o*** be the output of the neural network.

|o|Output|
|-|------|
|< 0.5| Authentic |
|>= 0.5 | Inauthentic |

#### Genetic Algorithm

A genetic algorithm is used to optimize the **Weights** and **Biases** above neural network.

1. Chromosome 

	- An array consisting of the weights and biases of the weights and biases of the network is taken to be the Genotype of the algorithm.
	- By the architecture of the above network, this genotype consists of 33 elements.

2. Fitness Function
	-	The **Mean Squared Error** loss function is used to compute the fitness of an individual.
	-	It is done in 2 steps:
		1. The weights and biases of the neural network are set according to the individual chromosome.
		2. The Network is then used to compute the output and find the MSE.
	- The genetic algorithm is used to **minimize** this fitness function.

3. Crossover
	- The **Uniform Crossover** function is used to generate offspring from their parents.
	- The Uniform crossover does not use any crossover point but instead creates offspring by swapping each corresponding allele of both parents.

4. Mutation
	- A **Gaussian Mutation** function is used to mutate the individuals.

### Performance

The network preformed very well and the genetic algorithm was efficient in optimizing the neural network. The algorithm converged to its optima in 51 generations.

1. MSE Loss : **0.0109**
2. Accuracy : **99.27 %**
