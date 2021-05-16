#include<iostream>

#define size 8

using namespace std;

struct stack {
	int top;
	int elements[size][2];

	stack() {
		top = -1;
	}
} s;
	
void push(stack &s, int e[2]) {
	if(s.top == size - 1) {
		//cout << "Overflow.";
	} else {
		s.top++;
		s.elements[s.top][0] = e[0];
		s.elements[s.top][1] = e[1];
	}
}

int* pop(stack &s) {
	if(s.top == -1) {
		//cout << "Underflow";
		return 0;
	} else {
		return s.elements[s.top--];
	}
}

int* stackTop(stack s) {
	if(s.top == -1) {
		//cout << "Underflow";
		return 0;
	} else {
		return s.elements[s.top];
	}
}

void printStack(stack s) {
	for(int i = 0; i <= s.top; i++)
		cout << s.elements[i][0] << ',' << s.elements[i][1] << ' ';
	cout << endl;
}

bool checkQueen(int *board, int N, int x, int y) {
	for(int i = 0; i < N; i++) {
		if(*(board + x*N + i) == 1 || *(board + i*N + y) == 1)
			return false;	
	}

	for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--)
 		if(*(board + i * N + j) == 1)
			return false;

	for(int i = x + 1, j = y + 1; i < N && j < N; i++, j++) {
		if(*(board + i * N + j) == 1) return false;
	}

	for(int i = x + 1, j = y - 1; i < N && j >= 0; i++, j--) {
		if(*(board + i * N + j) == 1) return false;
	}

	for(int i = x - 1, j = y + 1; i >= 0 && j < N; i--, j++) {
		if(*(board + i * N + j) == 1) return false;
	}
	return true;
}

bool nQueen(int *board, int N) {
	for(int i = 0; i < N; i++) {
		for(int j = 0; j < N; j++) {
			if(*(board + i * N + j) == 0 && checkQueen(board, N, i, j)) {
				int pos[] = {i, j};
				push(s, pos);
				*(board + i * N + j) = 1;
				if(s.top == size - 1) {
					return true;
				} else if(nQueen(board, N)) {
					return true;
				} else {
					pop(s);	
					*(board + i * N + j) = 0;
				}		
			}
		}
	}
	return false;
}

int main() {
	int *board = new int[size * size];
	for(int i = 0; i < size; i++)
		for(int j = 0; j < size; j++)
			*(board + i*size + j) = 0;
	if(nQueen(board, size)) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++)
				cout << *(board + i*size + j);
			cout << endl;
		}
	}	
	
	return 0;
}

