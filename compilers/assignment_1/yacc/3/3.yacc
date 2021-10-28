/*
	Yacc Grammar to validate a switch case construct
	Sample Input:
		switch (choice) {
			case 1:
				ans = x + y;
				break;
			case 2:
				ans = x - y;
				break;
			case 3:
				ans = x * y;
				break;
			default:
				ans = x / y;
				break;
		}
*/

%{
	#include <stdio.h>
	#include <stdlib.h>

	int yylex();
	void yyerror(char *s);
%}

%token ID NUM SWITCH CASE DEFAULT BREAK
%right '='
%left '+''-'
%left '*''/'
%right UMINUS

%%

S : ST {
	printf("Valid Input - Accepted.\n");
	exit(0);
};

ST : SWITCH '(' ID ')' '{' B '}';

B : C | C D;

C : C C | CASE NUM ':' E ';' | BREAK ';';

D : DEFAULT ':' E ';' BREAK ';' | DEFAULT ':' E ';';

E : ID'='E
	| E'+'E
	| E'-'E
	| E'*'E
	| E'/'E
	| ID
	| NUM;

%%
