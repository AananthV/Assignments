#include <iostream>

using namespace std;

struct node {
	int value;
	node* prev;
	node* next;
	
	node(int _value) {
		value = _value;
		prev = NULL;
		next = NULL;
	}
};

int countNodes(node *list) {
	int num = 0;
	if(list == NULL) return 0;
	node *t = list;
	do {
		num++;
		t = t->next;
	} while(t != list);
	return num;
}

void traverse(node *list) {
	node *t = list;
	do {
		cout << t->value << ' ';
		t = t->next;
	} while(t != list);
	cout << endl;
}

node *insertAtEnd(node *list, int value) {
	node *next = new node(value);
	if(list == NULL) {
		list = next;
		list->prev = list->next = list;
	} else {
		next->next = list;
		next->prev = list->prev;
		list->prev = next;
		next->prev->next = next;
	}
	return list;
}

node *reverse(node *list) {
	node *t = list;
	do {
		node *temp = t->prev;
		t->prev = t->next;
		t->next = temp;
		t = t->next; 
	} while(t != list);
	return list->next;
}

node *split(node *list, int pos) {
	if(pos >= countNodes(list)) {
		cout << "Position does not exist";
		return NULL;	
	}
	if(pos == 0) {
		cout << "Cant Split at position 1";
		return NULL;	
	}
	node *t = list;
	for(int i = 0; i < pos; i++) {
		t = t->next;
	}
	node *end = list->prev;
	t->prev->next = list;
	list->prev = t->prev;
	t->prev = end;
	end->next = t;
	return t;
}

int main() {
	node *list = NULL;
	int choice = 0;
	int temp = 0;
	do {
		cout << "1 - Insert, 2 - Reverse, 3 - Size of, 4 - Split, 0 - Exit\n";
		cin >> choice;
		switch(choice) {
			case 1:
				cout << "Enter Number to insert: ";
				cin >> temp;
				list = insertAtEnd(list, temp);
				cout << "List: ";
				traverse(list);
				break;
			case 2:
				cout << "Reversed List: ";
				list = reverse(list);
				traverse(list);
				break;
			case 3:
				cout << "Length: " << countNodes(list) << endl;
				break;
			case 4:
				cout << "Enter Position to Split At: ";
				cin >> temp;
				node *second = split(list, temp - 1);
				cout << "\nFirst List: ";
				traverse(list);
				cout << "Second List: ";
				traverse(second);
				break;
		}
	} while(choice != 0);
}
