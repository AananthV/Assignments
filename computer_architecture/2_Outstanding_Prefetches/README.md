## 2. Outstanding Prefetches

Done by **S. Pavithra (106118091)** and **V. Aananth (106118103)** for the computer architecture project (Set 5 Question 2).

## Question

Assume you are designing a hardware prefetcher for the unblocked matrix transposition code above. The simplest type of hardware prefetcher only prefetches sequential cache blocks after a miss. More complicated “non-unit stride” hardware prefetchers can analyze a miss reference stream and detect and prefetch non-unit strides. In contrast, software prefetching can determine non-unit strides as easily as it can determine unit strides. Assume prefetches write directly into the cache and that there is no “pollution” (overwriting data that must be used before the data that are prefetched). For best performance given a non-unit stride prefetcher, in the steady state of the inner loop how many prefetches must be outstanding at a given time?

## Answer

#### Assumptions

- Since the question given is incomplete we assume the following information as per **exercise 2.2** from the **"Computer Architecture - A Quantitative Approach (5th Edition)"** textbook.

- We make the following assumptions,

	1. Matrix

		- Assume that input and output matrices are stored **row major** order.
		- Assume that the matrix is of dimension $256 \times 256$.
		- Assume that the matrix consists of **double-precision numbers**.

	3. Memory and Execution

		- Assume that we are using a a processor with a **16 KB fully associative least recently used (LRU) replacement L1 data cache with 64 byte blocks**.
		- Assume that the L1 cache misses or prefetches require **16 cycles** and always hit in the L2 cache, and that the L2 cache can process a request every two processor cycles.
		- Assume that each iteration of the inner loop requires **4 cycles** if the data are present in the L1 cache.
		- Assume that the cache has a write-allocate fetch-on-write policy for write misses and that writing back dirty cache blocks (unrealistically) requires 0 cycles.

#### Solution

Given,

1. Size of each element = **8 Bytes** (Double Precision)
2. Size of matrix = $256 \times 256$ 
3. Size of cache = **16 kB**
4. Size of each cache block = **64 Bytes**

Now,

- Size of matrix in memory
	$$
	\begin{aligned}
	S_M &= 256 \times 256 \times 8 \text{ B} \\
	&= 524288 \text{ B} \\
	&= 512 \text{ kB}
	\end{aligned}
	$$

- Clearly the matrix is too large to fit into memory and requires multiple fetches.

- Since the size of each cache block is **64 Bytes** we can fetch **8** elements at a time.

- Since each L2 cache can process a request every **2 cycles**, prefetches can be initiated every **2 cycles**.

- Since it takes **4 cycles** per iteration of the inner loop, the number of prefetches is more than one. The memory system will hence be completely saturated with prefetches.

- Since the latency of a prefetch is **16 cycles**, and one starts every **2 cycles**.
	$$
	\text{No. of Outstanding prefetches } = \frac{16}{2} =8
	$$

Therefore, **number of outstanding prefetches = 8** at any given time.

----
