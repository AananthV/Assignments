---
title: |
        Deep Learning (CSHO 23) \ \
        Assignment 1
author:
    - "S. Pavithra (106118091)"
    - "V. Aananth (106118103)"
    - "Yash Shah (106118107)"
toc: true
documentclass: scrartcl
...

\newpage

# Inception v3

**Inception v3**, introduced in "*Rethinking the Inception Architecture for Computer Vision, Szegedy et al.*",is  a convolutional neural network architecture from the Inception family from **Google** that makes several improvements including using **Factorized 7 x 7 convolutions**, **Label Smoothing**,  and the use of an **Auxiliary classifer** to propagate label information lower down the network. One of the common uses of this architecture has been in aiding the research of **Leukemia** 

## 1.Factorizing Convolutions 

The aim of factorizing Convolutions is to reduce the number of connections/parameters without decreasing the network efficiency to reduce the computational cost.

### 1.1 Factorization Into Smaller Convolutions

We can reduce the number of parameters by replacing the bigger convolutions by the smaller ones. As shown below we can two 3x3 convolutions instead of one 5x5.

![](https://imgur.com/KzOomNX.png)

By using 1 layer of 5×5 filter, number of parameters = 5×5=25

By using 2 layers of 3×3 filters, number of parameters = 3×3+3×3=18

Hence the number of parameters used will be reduced by **28%**.

### 1.2 Factorization Into Asymmetric Convolutions

One 3×1 convolution followed by one 1×3 convolution replaces one 3×3 convolution as follows

![Asymmetric Convolution](https://imgur.com/Yi6FST9.png)

By using 3×3 filter, number of parameters = 3×3=9

By using 3×1 and 1×3 filters, number of parameters = 3×1+1×3=6

Number of parameters is reduced by **33%**

Using this property two set of $n \times 1$ and $1 \times n$ filters are used to replace the $n \times n$ filters in the model.

## 2. Auxillary Classifier
An auxiliary classifier is a small CNN inserted between layers during training, and the loss incurred is added to the **main network loss**.

One auxiliary classifier is used on the top of the last 17×17 layer, as shown below, which acts as a regularizer for the network.

![Auxillary Classifier](https://i.imgur.com/djenqSS.png)

## 3. Efficient Grid Size Reduction
The feature map downsizing in conventional models like VGG, AlexNet etc is done by pooling operations like Max Pooling. There are two possible conventional ways to do this:

1. Max Pooling followed by Convolutional layer
    - Network becomes too ***greedy*** when Max Pooling followed by Convolutional Layer is used thereby introducing a **representational bottleneck**.

2. Convolutional layer followed by Max Pooling
    - Network becomes **3 times more computationally expensive** when Convolutional Layer followed by Max Pooling is used. 
    
![Pooling followed by Conv (left), Conv folllowed by Pooling (right)](https://i.imgur.com/xy7HJF4.png)

To combat the bottlenecks of computational cost, a more efficient technique for grid size reduction was proposed as follows

![Grid Size Reduction](https://i.imgur.com/2kAoSSt.png)

The above diagram represents an *efficient way to reduce grid size* and avoiding the **representational bottleneck**.

The diagram on the right represents a *detailed architecture* of the grid size reduction and the one on bottom left represents the same solution but from the perspective of *grid sizes* rather than the operations.

With the efficient grid size reduction, **320 feature maps** are done by conv layer with stride of 2. **320 feature maps** are obtained by Max Pooling. And these 2 sets of feature maps are concatenated as **640 feature maps** and go to the next level of inception module.

## 4.Label Smoothing as Regularization
The purpose of label smoothing is to prevent the largest logit from becoming much larger than all others

$$
\text{new labels} = (1 - \epsilon) \times \text{one hot labels} + \frac{\epsilon}{K}
$$

here $\epsilon$ is 0.1 which is a hyperparameter and K is 1000 which is the number of classes. A kind of dropout effect observed in classifier layer.


## 5. Network Architecture 
The network Architecture described in the paper is as follows.

![Network Architecture](https://i.imgur.com/fyIurzk.png)

|    Type     | Patch Size / Stride | Input Size   |
|:-----------:| ------------------- | ------------ |
|    conv     | 3×3/2               | 299×299×3    |
|    conv     | 3×3/1               | 149×149×32   |
| conv padded | 3×3/1               | 147×147×32   |
|    pool     | 3×3/2               | 147×147×64   |
|    conv     | 3×3/1               | 73×73×64     |
|    conv     | 3×3/2               | 71×71×80     |
|    conv     | 3×3/1               | 35×35×192    |
| 3×Inception | As shown below (fig 5)    | 35×35×288    |
| 5×Inception | As shown below (fig 6)      | 17×17×768    |
| 2×Inception | As shown below (fig 7)      | 8×8×1280     |
|    pool     | 8 × 8               | 8 × 8 × 2048 |
|   linear    | logits              | 1 × 1 × 2048 |
|   softmax   | classifier          | 1 × 1 × 1000 |


![Inception Modules](https://i.imgur.com/DET4QK7.png)

\newpage

# MobileNet

**MobileNets**, introduced in "*MobileNets: Efficient Convolutional Neural Networks for Mobile Vision Applications, Howard et al, 2017.*" are a class of lightweight CNNs which use **Depthwise Separable Convolution** to reduce the model size and complexity. They are particularly useful for **mobile** and **embedded vision** applications.

## 1. Depthwise Separable Convolution

![Depthwise Seperable Convolution](https://i.imgur.com/1uQtSaF.png)

Depthwise separable convolution is a depthwise convolution followed by a pointwise convolution. This means that it performs a single convolution on each colour channel rather than combining all three and flattening it. This has huge performance improvements over traditional CNNs while having a minimal drop in accuracy.

1. **Depthwise convolution** is the channel-wise $D_K \times D_K$ spatial convolution.
2. **Pointwise convolution** is the 1×1 convolution used to **combine the outputs** of the Depthwise Convolution.

A standard convolution both filters and combines inputs into a new set of outputs in one step. The depthwise separable convolution splits this into two layers, a separate layer for filtering and a separate layer for combining. This factorization has the effect of drastically reducing computation and model size.

### 1.1 Computational Cost

Consider a convolutional layer with $M$ input channels, $N$ output channels, a kernel size of $D_K$, and a Feature map of size $D_F$. 
The **computational cost** of *Depthwise Seperable Convolution* is
$$
D_K . D_K . M . D_F . D_F + M . N . D_F . D_F
$$
- Where $D_K . D_K . M . D_F . D_F$ is the Depthwise Convolution Cost and $M . N . D_F . D_F$ is the Pointwise Convolution Cost.

On the other hand, the computational cost of *Standard Convlution* is
$$
D_K . D_K . M . N . D_F . D_F
$$

Therefore, the reduction in computation is
$$
\frac{D_K . D_K . M . D_F . D_F + M . N . D_F . D_F}{D_K . D_K . M . N . D_F . D_F}
= \frac{1}{N} + \frac{1}{D^2_K}
$$

With a filter size of $D_K$ a huge performance improvement close to $D_K^2$ is observed.

![](https://i.imgur.com/4saHkxC.png)


## 2. Network Architecture

The network architecture described in the paper is as follows

![](https://i.imgur.com/NOf4cRi.png)

The network consists of the following:

- Convolution Layer with Stride 2
- 6x Depthwise Seperable Convolution Layers with Depthwise Layer stride alternating between 1 and 2.
- 5x Depthwise Seperable Convolution Layers with Depthwise Layer stride 1.
- 2x Depthwise Seperable Convolution Layers with Depthwise Layer stride 2.
- Average Pool Layer
- Fully Connected Layer (FC)
- Softmax Layer

It is to be noted that *Batch Normalization (BN)* and *ReLU Activation* are applied after each *Convolution*. This looks like

![Standard Convolution (Left), Depthwise Seperable Convolution (Right)](https://i.imgur.com/uzPK3mC.png)

## 3. Global Hyper-Parameters

MobileNet introduces two simple global hyper-parameters that efficiently trade off between latency and accuracy. These hyper-parameters allow the model builder to choose the right sized model for their application based on the constraints of the problem.

### 3.1 Width Multiplier $\alpha$

Width Multiplier $\alpha$ is introduced to control the **number of channels or channel depth**, which makes $M$ become $\alpha M$. It is a value between 0 and 1, with typical settings of 1, 0.75, 0.5 and 0.25. Introduction of $\alpha$ reduces the computational cost to

$$
D_K . D_K . \alpha M . D_F . D_F + \alpha M . \alpha N . D_F . D_F
$$

The computational cost and number of parameters are reduced quadratically by roughly $\alpha^2$.

![](https://i.imgur.com/MWJbdXx.png)

### 3.2 Resolution Multiplier $\rho$

Resolution Multiplier $\rho$ is introduced to control the **input image resolution** of the network. $\rho$, like $\alpha$ takes a value between 0 and 1. Introduction of the Resolution Multiplier $\rho$ reduces the computational cost to

$$
D_K . D_K . \alpha M . \rho D_F . \rho D_F + \alpha M . \alpha N . \rho D_F . \rho D_F
$$
