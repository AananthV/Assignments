/*
	LEX Program to tokenize a valid if-else-if construct
*/

%{
	#include<stdio.h>
	#include "y.tab.h"
%}

alphabet [A-Za-z]
digit [0-9]

%%
[ \t\n] ; 
if return IF;
else return ELSE;
{digit}+ return NUM;
{alphabet}({alphabet}|{digit})* return ID;
"<=" return LE;
">=" return GE;
"==" return EQ;
"!=" return NE;
"||" return OR;
"&&" return AND;
. return yytext[0];
%%

void yyerror(char *s){
	fprintf(stderr, "Invalid Input - %s\n", s);
}

int yywrap(){
	return 1;
}

int main(){
	fprintf(stderr, "Enter the expression\n");
	yyparse();
	return 0;
}
