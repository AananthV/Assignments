/*
	Yacc Grammar to validate a repeat-until construct in pascal
	Sample Input:
		repeat
			fact := fact * n;
			n := n - 1;
		until n == 1;
*/

%{
	#include <stdio.h>
	#include <stdlib.h>

	int yylex();
	void yyerror(char *s);
%}

%token ID NUM LE GE EQ NE ASSIGN OR AND REPEAT UNTIL
%right ASSIGN
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

ST : REPEAT B UNTIL E;

B : B B | E ';';
   
E : E ASSIGN E | 
	E'+'E | 
	E'-'E | 
	E'*'E | 
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
