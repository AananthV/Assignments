#include<bits/stdc++.h> 
#include<iostream>

#define SPACES 10
#define size 10

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

struct Stack {
	int top;
	int elements[size];

	Stack() {
		top = -1;
	}
};
	
void push(Stack &s, char e) {
	if(s.top == size - 1) {
		//cout << "Overflow.";
	} else {
		s.elements[++s.top] = e;
	}
}

char pop(Stack &s) {
	if(s.top == -1) {
		//cout << "Underflow";
		return 0;
	} else {
		return s.elements[s.top--];
	}
}

char StackTop(Stack s) {
	if(s.top == -1) {
		//cout << "Underflow";
		return 0;
	} else {
		return s.elements[s.top];
	}
}

int isOperand(char v) {
	return (v >= 48 && v <= 57);
}

int isOperator(char o) {
	return o == '+' || o == '-' || o == '*' || o == '/' || o == '^' || o == '(' || o == ')';
}

int preceedance(char o) {
	switch(o) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
	}
}

int strlen(char *str) {
	int length = 0;
	while(*(str + length) != '\0') {
		length++;	
	}
	return length;
}

void printStack(Stack s) {
	for(int i = s.top; i > -1; i--) {
		cout << s.elements[i];	
	}
	cout<< endl;
}

char *getPostFix(char *infix) {
	char *postfix = new char(strlen(infix) + 1);
	int p_i = 0;

	Stack s;

	for(int i = 0; i < strlen(infix); i++) {
		char item = *(infix + i);
		if(isOperator(item)) {
			char top = StackTop(s);
			if(top != ')' && (top == 0 || top == '(' || preceedance(top) < preceedance(item))) {
				push(s, item);
			} else {
				char op = pop(s);
				if(op == ')') {
					op = pop(s);
					while(op != '(') {				
						*(postfix + p_i++) = op;
						op = pop(s);
					}
				} else {
					*(postfix + p_i++) = op;
				}
				i--;
			}
		} else {
			*(postfix + p_i++) = item;
		}
	}
	while(s.top > -1) {
		char op = pop(s);
		if(op == ')') {
			op = pop(s);
			while(op != '(') {				
				*(postfix + p_i++) = op;
				op = pop(s);
			}
		} else {
			*(postfix + p_i++) = op;
		}
	}
	return postfix;
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

void displayTree(tree *root, int space = 0) {
	if(!(root == NULL)) {
		space += SPACES;
		displayTree(root->right, space);
		cout << endl;
		int i = SPACES;
		for (; i < space; i++) {
        		cout<<" ";
		}
		cout << (char) root->value;
		displayTree(root->left, space);
	}
}

tree *constructTree(char *postfix) {
	stack<tree *> st;
	int l = strlen(postfix);
	for(int i = 0; i < l; i++) {
		char a = *(postfix + i);
		if(isOperator(a)) {
			tree *t = new tree(a);
			t->right = st.top();
			st.pop();
			t->left = st.top();
			st.pop();
			st.push(t);
		} else {
			tree *t = new tree(a);
			st.push(t);
		}	
	}
	return st.top();
}

int main() {
	tree *root = NULL;
	char infix[20];
	cin >> infix;
	char *postfix = getPostFix(infix);
	root = constructTree(postfix);
	displayTree(root);
	cout << endl;
}
