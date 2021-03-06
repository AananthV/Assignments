#include<iostream>
#include<string.h>

#define SPACES 10;

using namespace std;

struct tree {
  int value;
  char *ch;
  tree *left, *right;
  
  tree(char *c, int v) {
    value = v;
    ch = c;
    left = NULL;
    right = NULL;
  }
};

struct node {
	tree* t;
	node *next;

	node(tree* tr) {
		t = tr;
		next = NULL;
	}
};

char *strcat(char *str1, char *str2) {
	int l1 = strlen(str1), l2 = strlen(str2);
	char *str = new char(l1 + l2);
	for(int i = 0; i < l1; i++) {
		*(str + i) = *(str1 + i);	
	}	
	for(int i = 0; i < l2; i++) {
		*(str + l1 + i) = *(str2 + i);	
	}
	return str;
}

void printQueue(node *list) {
	node *temp = list;
	while(temp != NULL) {
		cout << temp->t->ch << temp->t->value << ' ';
		temp = temp->next;
	}
	cout << endl;
}

node* insertQueue(node* list, tree* tr) {
	node *next = new node(tr);
	if(list == NULL) {
		list = next;
	} else {
		node *temp = list;
		if(temp->t->value >= tr->value) {
			next->next = temp;
			list = next;		
		} else {
			while(temp->next != NULL && temp->next->t->value < tr->value) {
				temp = temp->next;		
			}
			next->next = temp->next;
			temp->next = next;
		}	
	}
	return list;
}

node* removeQueue(node *list) {
	if(list == NULL) {
		cout<<"Undeflow";	
	} else {
		list = list->next;
		return list;
	}
}

tree* constructHuffman(int a[26]) {
	node *list = NULL;
	for(int i = 0; i < 26; i++) {
		if(a[i] > 0) {
			char *t = new char[2];
			*t = i + 97;
			*(t + 1) = '\0';
			tree *n = new tree(t, a[i]);
			list = insertQueue(list, n);			
		}
	}
	while(list->next != NULL) {
		tree *t1 = list->t;
		list = removeQueue(list);
		tree *t2 = list->t;
		list = removeQueue(list);
		tree *n = new tree(strcat(t1->ch, t2->ch), t1->value + t2->value);
		n->left = t1;
		n->right = t2;
		list = insertQueue(list, n);
	}
	return list->t;
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
		cout << root->ch << root->value;
		displayTree(root->left, space);
	}
	cout << endl;
}

int main() {
	tree *root = NULL;
	char s[50];
	int frequency[26];
	for(int i = 0; i < 26; i++) {
		frequency[i] = 0;
	}
	cin >> s;
	for(int i = 0; i < strlen(s); i++) {
		frequency[s[i] - 97]++;
	}
	root = constructHuffman(frequency);
	displayTree(root);
	return 0; 
}
