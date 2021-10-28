/*
	LEX code to tokenize a valid postfix expression.
*/

%{
#include<stdio.h>
#include<math.h>
#include "y.tab.h"
%}

%%
[0-9] {
	yylval.dval = atof(yytext);
	return NUMBER;
}
\n return 0;
. return yytext[0];
%%

void yyerror(char *s) {
	fprintf(stderr, "%s\n", s);
}

int yywrap() {
	return 1;
}

int main() {
	fprintf(stderr, "Enter postfix expression: ");
	yyparse();
	return 0;
}
