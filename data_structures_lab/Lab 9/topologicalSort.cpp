#include<bits/stdc++.h>

using namespace std;

struct node {
	char value;
	vector<node*> out_neighbours;
	vector<int> in_neighbours;
	node(char v) {
		value = v;
		in_neighbours.clear();
		out_neighbours.clear();
	}
};

void addNode(vector<node*> &graph, char value) {
	node *n = new node(value);
	graph.push_back(n);
}

void addDirectedEdge(vector<node*> graph, int endVertices[2]) {
	if(endVertices[0] <= graph.size() && endVertices[1] <= graph.size()) {
		graph[endVertices[0] - 1]->out_neighbours.push_back(graph[endVertices[1] - 1]);
		graph[endVertices[1] - 1]->in_neighbours.push_back(endVertices[0] - 1);
	}
}

void topologicalSort(vector<node*> graph) {
	vector<bool> visited;
	visited.assign(graph.size(), false);
	node *temp;
	bool looper = false;
	while(!looper) {
		looper = true;
		for(int i = 0; i < graph.size(); i++) {
			if(visited[i] == false) {
				bool canVisit = true;
				for(int j = 0; j < graph[i]->in_neighbours.size(); j++) {
					if(!visited[graph[i]->in_neighbours[j]]) {
						canVisit = false;
					}
				}
				if(canVisit) {
					cout << graph[i]->value;
					visited[i] = true;
				}
			}
			if(!visited[i]) {
				looper = false;
			}
		}
	}
}

int main() {
	vector<node*> graph;
	int n, e[] = {0, 0};
	char v;
	cout << "Enter number of vertices: ";
	cin >> n;
	for(int i = 0; i < n; i++) {
		cout << "Enter value of vertice " << i + 1 << ": ";
		cin >> v;
		addNode(graph, v);
	}
	cout << "Enter number of directed edges: ";
	cin >> n;
	for(int i = 0; i < n; i++) {
		cout << "Enter end vertices indices of directed edge " << i + 1 << ": ";
		cin >> e[0] >> e[1];
		addDirectedEdge(graph, e);
	}
	cout << "Topological Sort: ";
	topologicalSort(graph);
	cout << endl;
}
