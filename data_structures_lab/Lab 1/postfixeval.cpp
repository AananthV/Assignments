#include<iostream>
#include<math.h>

#define size 10

using namespace std;

struct stack {
	int top;
	char elements[size];

	stack() {
		top = -1;
	}
};
	
void push(stack &s, char e) {
	if(s.top == size - 1) {
		//cout << "Overflow.";
	} else {
		s.elements[++s.top] = e;
	}
}

char pop(stack &s) {
	if(s.top == -1) {
		//cout << "Underflow";
		return 0;
	} else {
		return s.elements[s.top--];
	}
}

char stackTop(stack s) {
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

int strlen(char *str) {
	int length = 0;
	while(*(str + length) != '\0') {
		length++;	
	}
	return length;
}

void printStack(stack s) {
	for(int i = s.top; i > -1; i--) {
		cout << s.elements[i];	
	}
	cout<< endl;
}


int evalPostfix(char *postfix) {
	int result = 0;
	stack s;

	for(int i = 0; i < strlen(postfix); i++) {
		char item = *(postfix + i);
		if(isOperand(item)) {
			push(s, item - 48);		
		} else {
			int op1 = pop(s), op2 = pop(s);
			switch(item) {
				case '+':
					push(s, op1 + op2);
					break;
				case '-':
					push(s, op2 - op1);
					break;
				case '*':
					push(s, op1 * op2);
					break;
				case '/':
					push(s, op2 / op1);
					break;
				case '^':
					push(s, pow(op2, op1));
					break;						
			}
		}
	}
	
	return pop(s);
}

int main() {
	char postfix[] = "21+3*2^";
	cout << evalPostfix(postfix);
}
