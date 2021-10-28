/*
    LEX Code to Read a Paragraph and replace every 'e'
    with a 't'
*/

%%
e {printf("t");}
. {printf("%c", yytext[0]);}
%%

int yywrap(){}

int main() {
    yylex();
    return 0;
}