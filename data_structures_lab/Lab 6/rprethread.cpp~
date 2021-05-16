#include<iostream>

#define SPACES 10;

using namespace std;

struct tree {
  int value;
  tree *left, *right;
  bool rthread;
  
  tree(int v) {
    value = v;
    rthread = true;
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

tree* insert(tree *root, int x, tree *thread = NULL) {
	if(root == NULL) {
		root = new tree(x);	
	} else {
		if(x < root->value) {
			if(root->left == NULL) {
				root->left = new tree(x);	
				root->left->right = root;	
			} else {
				root->left = insert(root->left, x);			
			}
		} else if (x > root->value) {
			if(root->rthread == 1) {
				tree *t = root->right;
				root->rthread = 0;
				root->right = new tree(x);
				root->right->right = t;
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
		if(root->rthread == 0) 
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

void preOrder(tree *root) {
	tree *curr = root;
	while(curr!=NULL) {
		cout << curr->value;
		if(curr->left!=NULL)
		    	curr=curr->left;
		else if(curr->right!=NULL && curr->rthread==0)
		    	curr=curr->right;
		else {
		    	while(curr->right!=NULL && curr->rthread==1)      
		        	curr=curr->right;
		    	if(curr->right == NULL) break;
		    	else curr=curr->right;
		}
    	}
}

int main() {
	tree *root = NULL;
	int ch,n;
	do {
		cout << "\n1.Insert Node\n2.Display Tree\n3.Pre Order\n0. Exit\nEnter your choice: ";
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
				displayTree(root);
				break;
			case 3:
				preOrder(root);
				break;
		}
	} while (ch != 0);
	return 0; 
}
