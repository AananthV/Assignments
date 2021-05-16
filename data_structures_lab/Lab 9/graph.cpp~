#include<bits/stdc++.h>

using namespace std;

struct node {
	char value;
	vector<node*> neighbours;
	node(char v) {
		value = v;
		neighbours.clear();	
	}	
};

void addNode(vector<node*> &graph, char value) {
	node *n = new node(value);
	graph.push_back(n);	
}

void addEdge(vector<node*> graph, int endVertices[2]) {
	if(endVertices[0] <= graph.size() && endVertices[1] <= graph.size()) {
		graph[endVertices[0] - 1]->neighbours.push_back(graph[endVertices[1] - 1]);
		graph[endVertices[1] - 1]->neighbours.push_back(graph[endVertices[0] - 1]);
	}
}

void bfs(node* root) {
	queue<node*> search;
	vector<node*> visited;
	search.push(root);
	visited.push_back(root);
	node *temp;
	while(!search.empty()) {

		temp = search.front();
		
		cout << temp->value;
		
		for(int i = 0; i < temp->neighbours.size(); i++) {

			node *t = temp->neighbours[i];
			bool find = false;

			for(int j = 0; j < visited.size(); j++) {
				if(visited[j] == t) find = true;
			}

			if(!find) {
				search.push(t);
				visited.push_back(t);
			}

		}
		
		search.pop();
	}
}

void dfs(node* root) {
	stack<node*> search;
	vector<node*> visited;
	search.push(root);
	visited.push_back(root);
	node *temp;
	while(!search.empty()) {

		temp = search.top();

		cout << temp->value;

		search.pop();
		
		for(int i = 0; i < temp->neighbours.size(); i++) {

			node *t = temp->neighbours[i];
			bool find = false;

			for(int j = 0; j < visited.size(); j++) {
				if(visited[j] == t) find = true;
			}

			if(!find) {
				search.push(t);
				visited.push_back(t);
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
	cout << "Enter number of edges: ";
	cin >> n;
	for(int i = 0; i < n; i++) {
		cout << "Enter end vertice indices of edge " << i + 1 << ": ";
		cin >> e[0] >> e[1];
		addEdge(graph, e);
	}
	cout << "\nBFS: ";
	bfs(graph[0]);
	cout << "\nDFS: "; 	
	dfs(graph[0]);
	cout << endl;
}
