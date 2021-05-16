#include<bits/stdc++.h>

using namespace std;

struct edge {
	int weight;
	int end;
	edge(int w, int e) {
		weight = w;
		end = e;
	}
};

struct node {
	int id;
	char value;
	vector<edge*> edges;
	node(char v, int i) {
		id = i;
		value = v;
		edges.clear();	
	}	
};

void addNode(vector<node*> &graph, char value) {
	node *n = new node(value, graph.size());
	graph.push_back(n);	
}

void addEdge(vector<node*> graph, int endVertices[2], int weight) {
	if(endVertices[0] <= graph.size() && endVertices[1] <= graph.size()) {
		edge *e0 = new edge(weight, graph[endVertices[1] - 1]->id);
		graph[endVertices[0] - 1]->edges.push_back(e0);
		edge *e1 = new edge(weight, graph[endVertices[0] - 1]->id);
		graph[endVertices[1] - 1]->edges.push_back(e1);
	}
}

void dijkstra(vector<node*> graph) {
	int N = graph.size();

	vector<int> distance;
	vector<vector<node*> > paths;
	vector<bool> visited;

	distance.assign(N, -1);
	distance[0] = 0;
	for(int i = 0; i < N; i++) {
		vector<node*> path;
		paths.push_back(path);
	}
	visited.assign(N, false);
	visited[0] = true;

	int next_visit = 0;
	for(int i = 0; i < N - 1; i++) {
		for(int j = 0; j < graph[next_visit]->edges.size(); j++) {
			int visiting_node = graph[next_visit]->edges[j]->end;
			int node_distance = distance[i] + graph[next_visit]->edges[j]->weight;
			if(distance[visiting_node] == -1 || node_distance < distance[visiting_node]) {
				distance[visiting_node] = node_distance;
				paths[visiting_node] = paths[next_visit];
				paths[visiting_node].push_back(graph[next_visit]);
			}
		}
		int min_dist = -1;
		for(int j = 0; j < N; j++) {
			if(visited[j] == false && distance[j] != -1) {
				if(min_dist == -1 || distance[j] < distance[min_dist]) {
					min_dist = j;
				}
			}
		}
		visited[min_dist] = true;
		next_visit = min_dist;
		for(int j = 0; j < N; j++) {
			cout << visited[j] << " " << distance[j] << " | ";
		}
		cout << endl;
	}

	for(int i = 0; i < N; i++) {
		cout << "\n" << graph[i]->value << ":";
		for(int j = 0; j < paths[i].size(); j++) {
			cout << " " << paths[i][j]->value;
		}
		cout << " | Total Distance: " << distance[i];
	}
}

int main() {
	vector<node*> graph;
	int n, e[] = {0, 0}, w;
	char v;
	cout << "Enter number of vertices: ";
	cin >> n;
	for(int i = 0; i < n; i++) {
		cout << "Enter value of vertice " << i + 1 << ": ";
		cin >> v;
		addNode(graph, v);	
	}
	cout << "Enter number of edges: ";
	cin >> n;
	for(int i = 0; i < n; i++) {
		cout << "Enter end vertice indices and weight of edge " << i + 1 << ": (vertice1 vertice2 weight)";
		cin >> e[0] >> e[1] >> w;
		addEdge(graph, e, w);
	}
	dijkstra(graph);
	cout << endl;
}

//4 a b c d 4 1 2 1 1 3 5 2 3 2 2 4 2
