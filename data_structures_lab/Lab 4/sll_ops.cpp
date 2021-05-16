#include <iostream>

using namespace std;

struct node {
	int value;
	node* next;
	
	node(int _value) {
		value = _value;
		next = NULL;
	}
};

void traverse(node *list) {
	node *t = list;
	while(t != NULL) {
		cout << t->value << ' ';
		t = t->next;
	}
	cout << endl;
}

int countNodes(node *list) {
	int num = 0;
	node *t = list;
	while(t != NULL) {
		t = t->next;
		num++;
	}
	return num;
}

node *insertAtEnd(node *list, int value) {
	node *next = new node(value);
	if(list == NULL) {
		list = next;
	} else {
		node *t = list;
		while(t->next != NULL) {
			t = t->next;
		}
		t->next = next;
	}
	return list;
}

node *nodeAt(node *list, int index) {
	node *t = list;
	for(int i = 0; i < index; i++) {
		t = t->next;
	}
	return t;
}

node *insertSort(node *list) {
	int pos = 0;	
	do {
		node *tempMin = nodeAt(list, pos);
		node *min = nodeAt(list, pos);
		node *temp = min->next;
		int minPos = pos, tempPos = pos + 1;
		while(temp != NULL) {
			if(temp->value < min->value) {
				min = temp;
				minPos = tempPos;
			}
			temp = temp->next;
			tempPos++;
		}
		if(minPos != pos) {
			nodeAt(list, minPos - 1)->next = min->next;
			min->next = tempMin;
			if(pos != 0) {
				node *prevMin = nodeAt(list, pos - 1);
				prevMin->next = min;
			} else {
				list = min;
			}
		}
		pos++;
	} while(nodeAt(list, pos) != NULL);
	return list;
}

void alternatingSplit(node *list) {
	node *l1 = NULL, *l2 = NULL, *t = list;
	int counter = 0;
	while(t != NULL) {
		if(counter % 2 == 0) {
			l1 = insertAtEnd(l1, t->value);
		} else {
			l2 = insertAtEnd(l2, t->value);
		}
		counter++;
		t = t->next;
	}
	cout << "List 1A: ";
	traverse(l1);
	cout << "List 1B: ";
	traverse(l2);
}

void appendLists(node *l1, node *l2) {
	node *t = l1;
	while(t->next != NULL && t->next != l2) {
		t = t->next;
	}
	t->next = l2;
}

int main() {
	node *list2 = NULL;
	list2 = insertAtEnd(list2, 3);
	list2 = insertAtEnd(list2, 4);
	list2 = insertAtEnd(list2, 5);

	node *list = NULL;
	int choice = 0;
	int temp = 0;
	do {
		cout << "1 - Insert, 2 - Sort, 3 - Alternate Split, 4 - Append, 5 - Length, 0 - Exit\n";
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
				cout << "Sorted List: ";
				list = insertSort(list);
				traverse(list);
				break;
			case 3:
				cout << "Alternate Splitting..\n";
				alternatingSplit(list);
				break;
			case 4:
				cout << "List 2: ";
				traverse(list2);
				cout << "Appending List 2 to List....\n";
				appendLists(list, list2);
				list2 = NULL;
				cout << "List: ";
				traverse(list);
				break;
			case 5:
				cout << "Length: " << countNodes(list) << endl;
				break;
		}
	} while(choice != 0);
}
