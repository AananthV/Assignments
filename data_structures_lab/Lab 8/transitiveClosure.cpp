#include<bits/stdc++.h>

using namespace std;

bool* getTransitiveClosure(bool *graph, int N) {
	bool *tgraph = new bool[N * N];
	for(int i = 0; i < N; i++) {
		for(int j = 0; j < N; j++) {
			*(tgraph + i * N + j) = *(graph + i * N + j);
		}
	}
	for(int k = 0; k < N; k++) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				*(tgraph + i * N + j) = *(tgraph + i * N + j) || (*(tgraph + k * N + j) && *(tgraph + i * N + k));
			}
		}
	}
	return tgraph;
}

void printGraph(bool *graph, int N) {
	for(int i = 0; i < N; i++) {
		for(int j = 0; j < N; j++) {
			cout << *(graph + i * N + j) << " ";
		}
		cout << endl;
	}
}

int main() {
	int N;
	bool *graph;
	cout << "Enter size of graph: ";
	cin >> N;
	graph = new bool[N * N];
	cout << "Enter graph: \n";
	for(int i = 0; i < N; i++) {
		for(int j = 0; j < N; j++) {
			cin >> *(graph + i * N + j);
		}
	}
	cout << "Transitive Closure is: \n";
	printGraph(getTransitiveClosure(graph, N), N);
}
