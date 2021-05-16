#include <iostream>

using namespace std;

struct node {
	int value;
	node *next;

	node(int val = 0) {
		value = val;
		next = NULL;	
	}
};

void printQueue(node *list) {
	node *temp = list;
	while(temp != NULL) {
		cout << temp->value << " ";
		temp = temp->next;
	}
	cout << endl;
}

node* insert(node* list, int value) {
	node *next = new node(value);
	if(list == NULL) {
		list = next;
	} else {
		node *temp = list;
		if(temp->value >= value) {
			next->next = temp;
			list = next;		
		} else {
			while(temp->next != NULL && temp->next->value < value) {
				temp = temp->next;		
			}
			next->next = temp->next;
			temp->next = next;
		}	
	}
	return list;
}

node* remove(node *list) {
	if(list == NULL) {
		cout<<"Undeflow";	
	} else {
		node *temp = list;
		list = list->next;
		delete temp;
		return list;
	}
}

int main() {
	node *queue = NULL;
	int choice = 0, value = 0;
	do {
		cout << "\n1 - Insert, 2 - Delete, 0 - Exit\n";
		cin >> choice;
		switch(choice) {
			case 0:
				break;
			case 1:
				cout << "\nEnter Value to Insert: ";
				cin >> value;
				queue = insert(queue, value);
				cout << "Queue: ";
				printQueue(queue);
				break;
			case 2:
				queue = remove(queue);
				cout << "Queue: ";
				printQueue(queue);
				break;
			default:
				cout << "Enter valid option.";
				break;				
		}
	} while(choice != 0);	
}
