#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 1000000
#define T 256

/*
<<<B, T>>>

gridDim.x = B
blockDim.x = T
blockIdx.x = 0 ... B - 1
threadIdx.x = 0 ... T - 1
*/


/*
clP - Cond0tional Likelihood of Parents (1x6)
clC - Conditional Likelihood of Children (1x12)
clPC - Transition Probability of Parent -> Child (1x12) 
*/

__global__ void compute_parent_likelihood (float *clP, float *clC, float *tiPC) {
    for (int p_id = threadIdx.x; p_id < 6 * N; p_id += T) {
        int l = p_id * 2, r = p_id * 2 + 1;

        clP[p_id] = tiPC[l] * clC[l] + tiPC[r] * clC[r];
    }
}

int main() {
    // Define and allocate host memory
    float *clP, *clC, *tiPC;
    
    clP = (float*) malloc(sizeof(float) * 6 * N);
    clC = (float*) malloc(sizeof(float) * 12 * N);
    tiPC = (float*) malloc(sizeof(float) * 12 * N);

    // Define and allocate device memory
    float *d_clP, *d_clC, *d_tiPC;

    cudaMalloc((void**) &d_clP, sizeof(float) * 6 * N);
    cudaMalloc((void**) &d_clC, sizeof(float) * 12 * N);
    cudaMalloc((void**) &d_tiPC, sizeof(float) * 12 * N);

    // Initialize with random values
    for (int i = 0; i < 12 * N; i++) {
        clC[i] = (float)rand()/(float)(RAND_MAX);
    }

    for (int i = 0; i < 6 * N; i++) {
        tiPC[2*i] = (float)rand()/(float)(RAND_MAX);
        tiPC[2*i + 1] = 1 - tiPC[2*i];
    }

    // Copy from host to device memory
    cudaMemcpy((void*) d_clC, (void*) clC, sizeof(float) * 12 * N, cudaMemcpyHostToDevice);
    cudaMemcpy((void*) d_tiPC, (void*) tiPC, sizeof(float) * 12 * N, cudaMemcpyHostToDevice);

    // Run kernel N times (to benchmark)
    compute_parent_likelihood<<<1, T>>>(d_clP, d_clC, d_tiPC);

    // Copy from device to host memory
    cudaMemcpy((void*) clP, (void*) d_clP, sizeof(float) * 6 * N, cudaMemcpyDeviceToHost);

    // Print first 6
    printf("First 6 CLs: ");
    for (int i = 0; i < 6; i++) {
        printf("%f ", clP[i]);
    }
    printf("\n");

    // Validation
    int errors = 0;
    for (int t = 0; t < N; t++) {
        for (int i = 0; i < 6; i++) {
            int id = t * 6 + i;
            int l = id * 2, r = id * 2 + 1;
            if (clP[id] - (clC[l] * tiPC[l] + clC[r] * tiPC[r]) > 1e-6) {
                errors++;
            }
        }
    }
    printf("Errors: %d\n", errors);

    // Free host memory
    free(clP);
    free(clC);
    free(tiPC);

    // Free device memory
    cudaFree(d_clP);
    cudaFree(d_clC);
    cudaFree(d_tiPC);

    return 0;
}