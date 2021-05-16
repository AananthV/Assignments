#include<iostream>

#define SPACES 10;

using namespace std;

int max(int a, int b) {
	return a > b ? a : b;
}

struct tree {
  int value, height;
  tree *left, *right;
  
  tree(int v) {
    value = v;
    height = 1;
    left = NULL;
    right = NULL;
  }
};

int getHeight(tree *root) {
	return root != NULL ? root->height : 0;
}

int getBalance(tree *root) {
	return getHeight(root->left) - getHeight(root->right);
}

void updateHeight(tree *x) {
	if(x != NULL)
		x->height = max(getHeight(x->left), getHeight(x->right)) + 1;
}

tree *rotateLeft(tree *x) {
	tree *y = x->right;
	tree *t = y->left;
	
	y->left = x;
	x->right = t;
	
	updateHeight(x);
	updateHeight(y);

	return y;
}

tree *rotateRight(tree *x) {
	tree *y = x->left;
	tree *t = y->right;
	
	y->right = x;
	x->left = t;
	
	updateHeight(x);
	updateHeight(y);

	return y;
}

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
			root->left = insert(root->left, x);			
		} else if (x > root->value) {
			root->right = insert(root->right, x);
		}
	}

	updateHeight(root);

	int b = getBalance(root);
	
	if(b > 1 && x < root->left->value) {
		root = rotateRight(root);
	} else if (b < -1 && x > root->right->value) {
		root = rotateLeft(root);
	} else if (b > 1 && x > root->left->value) {
		root->left = rotateLeft(root->left);
		root = rotateRight(root);	
	} else if (b < -1 && x < root->right->value) {
		root->right = rotateRight(root->right);
		root = rotateLeft(root);	
	}
	return root;
}

tree* deleteNode(tree *root, int x) {
	if(root == NULL) {
		cout << "Node not found.";
		return root;
	}
	if(x == root->value) {
		if(root->left == NULL) {
			tree *t = root;
			root = root->right;
			delete t;			
		} else if(root->right == NULL) {
			tree *t = root;
			root = root->left;
			delete t;				
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

	if(root == NULL) return root;

	updateHeight(root);
	
	int b = getBalance(root);
	
	if(b > 1) {
		int bl = getBalance(root->left);
		if(bl == -1) {
			root->left = rotateLeft(root->left);
			root = rotateRight(root);		
		} else {
			root = rotateRight(root);		
		}
	} else if (b < -1) {
		int br = getBalance(root->right);
		if(br == -1) {
			root->right = rotateRight(root->right);
			root = rotateLeft(root);		
		} else {
			root = rotateLeft(root);		
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
	updateHeight(NULL);
	int ch,n;
	do {
		cout << "\n1.Insert Node\n2.Delete Node\n3.Display Tree\n0. Exit\nEnter your choice: ";
		cin >> ch;
		switch(ch){
			case 1:
				cout << "\nEnter value of node:";
				cin >> n;
				root=insert(root,n);	
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
