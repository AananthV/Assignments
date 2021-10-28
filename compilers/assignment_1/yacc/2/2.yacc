/*
	Yacc Grammar to validate an if-else-if construct.
	Sample Input:
		if (a < b && a < c) {
			min = a;
		} else if (b < c) {
			min = b;
		} else {
			min = c;
		}
*/

%{
	#include <stdio.h>
	#include <stdlib.h>

	int yylex();
	void yyerror(char *s);
%}

%token ID NUM LE GE EQ NE OR AND ELSE IF
%right '='
%left AND OR
%left '<' '>' LE GE EQ NE
%left '+''-'
%left '*''/'
%right UMINUS
%left '!'

%%

S : ST {
	printf("Valid Input - Accepted.\n");
	exit(0);
};

ST :  
	IF '(' E ')' '{' B '}' EFL ELSE '{' B '}' |
	IF '(' E ')' '{' B '}' EFL |
	IF '(' E ')' '{' B '}'

EFL : EFL EI | EI;

EI : ELSE IF '(' E ')' '{' B '}';

B : B B | E ';';   

E : E'='E | 
	E'+'E | 
	E'*'E |
	E'-'E | 
	E'/'E | 
	E'<'E | 
	E'>'E | 
	E LE E | 
	E GE E | 
	E EQ E | 
	E NE E | 
	E OR E | 
	E AND E | 
	ID | 
	NUM;

%%
