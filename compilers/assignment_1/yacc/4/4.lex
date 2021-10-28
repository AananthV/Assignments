/*
	LEX Program to tokenize a repeat-until construct in pascal
*/

%{
	#include<stdio.h>
	#include "y.tab.h"
%}

alphabet [A-Za-z]
digit [0-9]

%%
[ \t\n] ; 
repeat return REPEAT;
until return UNTIL;
{digit}+ return NUM;
{alphabet}({alphabet}|{digit})* return ID;
"<=" return LE;
">=" return GE;
"==" return EQ;
"!=" return NE;
"||" return OR;
"&&" return AND;
":=" return ASSIGN;
. return yytext[0];
%%

void yyerror(char *s){
	fprintf(stderr, "Invalid - %s\n", s);
}

int yywrap(){
	return 1;
}

int main(){
	fprintf(stderr, "Enter the expression\n");
	yyparse();
	return 0;
}
