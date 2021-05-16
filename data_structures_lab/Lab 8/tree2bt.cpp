#include<bits/stdc++.h>

#define SPACES 10;

using namespace std;

struct node {
	int value;
	node *son;
	node *next;
	node(int v) {
		value = v;
		son = NULL;
		next = NULL;
	}
};

node *newTree() {
	node *tree;
	int v;
	bool children, siblings;
	cout << "Enter node value: ";	
	cin >> v;
	tree = new node(v);
	cout << "Does the node" << v << " have children? (1/0): ";
	cin >> children;
	if(children) {
		cout << "Enter Child Data: \n";
		tree->son = newTree();
	}
	cout << "Does the node" << v << " have siblings? (1/0): ";
	cin >> siblings;
	if(siblings) {
		cout << "Enter Sibling Data: \n";
		tree->next = newTree();	
	}
	return tree;
}

void displayTree(node *root, int space = 0) {
	if(!(root == NULL)) {
		displayTree(root->next, space);
		cout << endl;
		for(int i = 0; i < space; i++) {
			cout << " ";	
		}
		cout << root->value;
		space += SPACES;
		displayTree(root->son, space);
	}
}

void displayBinaryTree(node *root, int space = 0) {
	if(!(root == NULL)) {
		space += SPACES;
		displayBinaryTree(root->next, space);
		cout << endl;
		int i = SPACES;
		for (; i < space; i++) {
        		cout<<" ";
		}
		cout << root->value;
		displayBinaryTree(root->son, space);
	}
}

int main() {
	node *root = newTree();
	cout << "Tree Representation: ";
	displayTree(root);
	cout << endl;
	cout << "Binary Tree Representation: ";
	displayBinaryTree(root);
	cout << endl;
}
