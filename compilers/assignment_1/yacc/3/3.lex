/*
	LEX Program to tokenize a valid switch case construct
*/

%{
	#include<stdio.h>
	#include "y.tab.h"
%}

alphabet [a-zA-Z]
digit [0-9]

%%
[ \t\n] ;
switch return SWITCH;
case return CASE;
break return BREAK;
default return DEFAULT;
{digit}+ return NUM;
{alphabet}({alphabet}|{digit})* return ID;
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
