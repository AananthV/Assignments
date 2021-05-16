#include<iostream>

#define SPACES 10;

using namespace std;

char colors[2] = {'R', 'B'};

int max(int a, int b) {
	return a > b ? a : b;
}

struct tree {
  int value, color;
  tree *left, *right;
  
  tree(int v) {
    value = v;
    color = 0;
    left = NULL;
    right = NULL;
  }
};

tree *rotateLeft(tree *x) {
	tree *y = x->right;
	tree *t = y->left;
	
	y->left = x;
	x->right = t;

	return y;
}

tree *rotateRight(tree *x) {
	tree *y = x->left;
	tree *t = y->right;
	
	y->right = x;
	x->left = t;

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
			if(root->color == 1 && root->left->color == 0) {
				if(root->left->left != NULL && root->left->left->color == 0) {
					if(root->right == NULL || root->right->color == 1) {
						root = rotateRight(root);
						root->color = 1;
						root->left->color = 0;
						root->right->color = 0;
					} else {
						root->color = 0;
						root->left->color = 1;
						root->right->color = 1;					
					}		
				} else if (root->left->right != NULL && root->left->right->color == 0) {
					if(root->right == NULL || root->right->color == 1) {
						root->left = rotateLeft(root->left);	
						root = rotateRight(root);
						root->color = 1;
						root->left->color = 0;
						root->right->color = 0;
					} else {
						root->color = 0;
						root->left->color = 1;
						root->right->color = 1;					
					}				
				}
			}		
		} else if (x > root->value) {
			root->right = insert(root->right, x);
			if(root->color == 1 && root->right->color == 0) {
				if(root->right->left != NULL && root->right->left->color == 0) {
					if(root->left == NULL || root->left->color == 1) {
						root->right = rotateRight(root->right);						
						root = rotateLeft(root);
						root->color = 1;
						root->left->color = 0;
						root->right->color = 0;
					} else {
						root->color = 0;
						root->left->color = 1;
						root->right->color = 1;					
					}		
				} else if (root->right->right != NULL && root->right->right->color == 0) {
					if(root->left == NULL || root->left->color == 1) {		
						root = rotateLeft(root);
						root->color = 1;
						root->left->color = 0;
						root->right->color = 0;
					} else {
						root->color = 0;
						root->left->color = 1;
						root->right->color = 1;					
					}				
				}
			}
		}
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
		cout << root->value << ' ' << colors[root->color];
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
				root=insert(root,n);
				if(root->color == 0) root->color = 1;
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
