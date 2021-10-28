/*
	Yacc Grammar to evaluate a postfix expression.
	Operators include +, -, *, and /.
	Sample Input:
		123+*4-56+*
*/

%{
	#include<stdio.h>

	int yylex();
	void yyerror(char *s);

	float ans = 0;
%} 

%union {
 float dval;
} 
%token <dval> NUMBER
%left '+' '-'
%left '*' '/'
%nonassoc UMINUS
%type <dval> state
%type <dval> exp
%type <dval> N

%%

state : exp N {};

exp : NUMBER {
		$$ = $1;
		ans = $$;
	} | 
	exp exp '+' {
		$$ = $1+$2;
		ans = $$;
	} | 
	exp exp '-' {
		$$ = $1-$2;
		ans = $$;
	} | 
	exp exp '*' {
		$$ = $1*$2;
		ans = $$;
	} | 
	exp exp '/' {
		$$ = $1/$2;
		ans = $$;
	};

N : {
	printf("Answer: %f\n", ans);
};

%%
