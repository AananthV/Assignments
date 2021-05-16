#include<iostream>

using namespace std;

int strlen(char *str) {
	int length = 0;
	while(*(str + length) != '\0') {
		length++;	
	}
	return length;
}

char *toUpper(char *str) {
	char *strc = new char(strlen(str));
	for(int i = 0; i <= strlen(str); i++) {
		if(*(str + i) >= 97 && *(str + i) <= 122) {
			*(strc + i) = *(str + i) - 32;		
		} else {
			*(strc + i) = *(str + i);
		}
	}
	return strc;
}

char *toLower(char *str) {
	char *strc = new char(strlen(str));
	for(int i = 0; i <= strlen(str); i++) {
		if(*(str + i) >= 65 && *(str + i) <= 90) {
			*(strc + i) = *(str + i) + 32;		
		} else {
			*(strc + i) = *(str + i);
		}
	}
	return strc;
}

bool strcmp(char *str1, char *str2) {
	int l1 = strlen(str1), l2 = strlen(str2);
	if(l1 != l2) return false;
	for(int i = 0; i < l1; i++) {
		if(*(str1 + i) != *(str2 + i)) return false;	
	}
	return true;
}

bool strcmpi(char *str1, char *str2) {
	return strcmp(toLower(str1), toLower(str2));
}

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

char *strrev(char *str) {
	int l = strlen(str);
	char *strc = new char(l);
	for(int i = 0; i < l; i++) {
		*(strc + i) = *(str + l - i - 1);
	}
	*(strc + l) = '\0';
	return strc;
}

void strcpy(char *str1, char *str2) {
	int l = strlen(str2);
	if(strlen(str1) < l) {
		cout << "String 1 too small";
		return;	
	}
	for(int i = 0; i <= l; i++) {
		*(str1 + i) = *(str2 + i);
	}
}

char *strstr(char *str, int begin, int num) {
	int l = strlen(str);
	if(begin < 0 || num < 0 || begin + num > l) {
		char error[] = "Invalid";
		return error;	
	}
	char *strstr = new char(num);
	for(int i = 0; i < num; i++) {
		*(strstr + i) = *(str + begin + i);	
	}
	return strstr;
}

bool strsym(char *str) {
	return strcmp(strrev(str), str);
}

int main() {
	char a[] = "abcdEF", b[] = "AbCDeF", c[] = "abcba";
	cout << "A = " << a << ", B = " << b << endl;
	cout << "Length of A: " << strlen(a) << endl;
	cout << "toUpper A: " << toUpper(a) << endl;
	cout << "toLower A: " << toLower(a) << endl;
	cout << "A == B? " << strcmp(a, b) << endl;
	cout << "A == B? (Case Independent) " << strcmpi(a, b) << endl;
	cout << "A + B : " << strcat(a, b) << endl;
	cout << "Reverse of A : " << strrev(a) << endl;
	cout << "Letters 2 - 5 of A: " << strstr(a, 1, 3) << endl;
	cout << "Copying B to A..." << endl;
	strcpy(a, b);
	cout << "A = " << a << endl;
	cout << "Is A Palindrome? " << strsym(a) << endl;
	cout << "C = " << c << endl;
	cout << "Is C Palindrome? " << strsym(c) << endl;	
}
