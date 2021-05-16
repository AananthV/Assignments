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

void printStack(stack s) {
	for(int i = s.top; i > -1; i--) {
		cout << s.elements[i];	
	}
	cout<< endl;
}

char *getPostFix(char *infix) {
	char *postfix = new char(strlen(infix) + 1);
	int p_i = 0;

	stack s;

	for(int i = 0; i < strlen(infix); i++) {
		char item = *(infix + i);
		if(isOperator(item)) {
			char top = stackTop(s);
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
					push(s, op1 - op2);
					break;
				case '*':
					push(s, op1 * op2);
					break;
				case '/':
					push(s, op1 / op2);
					break;
				case '^':
					push(s, pow(op1, op2));
					break;						
			}
		}
	}
	
	return pop(s);
}

int main() {
	char infix[] = "(a^b+c)^(a/b)";
	cout << getPostFix(infix);

	char postfix[] = "12+2-";
	cout << evalPostfix(postfix);
}
