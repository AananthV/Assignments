#include<iostream>

#define SPACES 10;

using namespace std;

struct tree {
  int value;
  tree *left, *right;
  
  tree(int v) {
    value = v;
    left = NULL;
    right = NULL;
  }
};

void inorder(tree *t) {
	if(t != NULL) {
		inorder(t->left);
		cout << t->value;
		inorder(t->right);	
	}
}

tree *minValueNode(tree *root) {
	tree* current = root; 
    	while (current && current->left != NULL) 
        	current = current->left; 
    	return current; 
}

tree* insert(tree *root, int x) {
	if(root == NULL) {
		root = new tree(x);	
	} else {
		if(x < root->value) {
			if(root->left == NULL) {
				root->left = new tree(x);		
			} else {
				root->left = insert(root->left, x);			
			}
		} else if (x > root->value) {
			if(root->right == NULL) {
				root->right = new tree(x);
			} else {
				root->right = insert(root->right, x);
			}	
		}
	}
	return root;
}

tree* deleteNode(tree *root, int x) {
	if(root == NULL) {
		cout << "Node not found.";	
	} else {
		if(x == root->value) {
			if(root->left == NULL) {
				tree *t = root->right;
				delete root;
				return t;			
			} else if(root->right == NULL) {
				tree *t = root->left;
				delete root;
				return t;			
			} else {
				tree *t = minValueNode(root->right);
				root->value = t->value;
				root->right = deleteNode(root->right, t->value);	
			}		
		} else if(x < root->value) {
			root->left = deleteNode(root->left, x);
		} else if (x > root->value) {
			root->right = deleteNode(root->right, x);	
		}	
	}
	return root;
}

void displayTree(tree *root, int space = 0) {
	if(!(root == NULL)) {
		space += SPACES;
		displayTree(root->right, space);
		cout << endl;
		int i = SPACES;
		for (; i < space; i++) {
        		cout<<" ";
		}
		cout << root->value;
		displayTree(root->left, space);
	}
}

int main() {
	tree *root = NULL;
	int ch,n;
	do {
		cout << "\n1.Insert Node\n2.Delete Node\n3.Display Tree\n0. Exit\nEnter your choice: ";
		cin >> ch;
		switch(ch){
			case 1:
				cout << "\nEnter value of node:";
				cin >> n;
				if(root==NULL)
					root=insert(root,n);	
				else
					insert(root,n);
				break;
			case 2:
				cout << "\nEnter value to be deleted: ";
				cin >> n;
				root=deleteNode(root,n);
				break;
			case 3:
				displayTree(root);
				break;
		}
	} while (ch != 0);
	return 0; 
}
