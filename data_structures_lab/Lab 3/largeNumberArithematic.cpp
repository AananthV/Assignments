#include <iostream>
#include <string.h>

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

node *insertAtBeg(node *list, int value) {
	node *next = new node(value);
	if(list == NULL) {
		list = next;
		list->prev = list->next = list;
	} else {
		next->next = list;
		next->prev = list->prev;
		list->prev = next;
		next->prev->next = next;
		list = list->prev;
	}
	return list;
}

node *addBigInt(node *num1, node *num2) {
	node *t1 = num1->prev, *t2 = num2->prev, *num3 = NULL;
	if(t1 == NULL || t2 == NULL) return NULL;
	int carry = 0;
	do {
		int sum = t1->value + t2->value + carry;
		carry = sum / 10000;
		sum = sum % 10000;
		num3 = insertAtBeg(num3, sum);
		t1 = t1->prev;
		t2 = t2->prev;
	} while(t1 != num1->prev && t2 != num2->prev);
	while(t1 != num1->prev) {
		int sum = t1->value + carry;
		carry = sum / 10000;
		num3 = insertAtBeg(num3, sum);
		t1 = t1->prev;
	}
	while(t2 != num2->prev) {
		int sum = t2->value + carry;
		carry = sum / 10000;
		num3 = insertAtBeg(num3, sum);
		t2 = t2->prev;
	}
	return num3;
}

void printNum(node *num) {
	if(num == NULL) {
		cout << '0';
	} else {
		node *t = num;
		do {
			cout << t->value;
			t = t->next;
		} while(t != num);
	}
	cout << endl;
}

node *stringToNumList(string str) {
	int l = str.length();
	node *num = NULL;
	for(int i = l; i >= 0; i -= 4) {
		int beg = i - 4 >= 0 ? i - 4 : 0;
		int len = i - 4 >= 0 ? 4 : i;
		string s = str.substr(beg, len);
		int number = 0;
		for(int j = 0; j < s.length(); j++) {
			number = number * 10 + s[j] - 48;
		}
		num = insertAtBeg(num, number);
	}
	return num;
}

int main() {
	node *num1 = stringToNumList("123456789"), *num2 = stringToNumList("1112131415");
	cout << "Num1: ";
	traverse(num1);
	cout << "Num2: ";
	traverse(num2);
	cout << "Num1 + Num2: ";
	traverse(addBigInt(num1, num2));
	return 0;
}


