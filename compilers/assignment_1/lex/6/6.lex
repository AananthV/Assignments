/*
    LEX Code to Convert an c++ declaration 
    to a c declaration.
    Eg:
        Input:
            int a = 5;
            char s = 's';
            bool flag = true;
        Output:
            int a;
            a = 5;
            char s;
            s = 's';
            bool flag;
            flag = true;
*/

type int|float|char|bool
id [a-zA-Z][a-zA-Z0-9_]*
no [0-9]*
str \".*\"
char \'.*\'
bool true|false

%%
{type}\ {id}\ =\ ({id}|{no}|{str}|{char}|{bool})\; {
    int index = 0;
    int space = -1;
    while(yytext[index] != '=') {
        if (yytext[index] == ' ') {
            if (space != -1) {
                index++;
                continue;
            }
            space = index;
        }
        printf("%c", yytext[index++]);
    }
    printf(";\n");

    for(index = space + 1; index < yyleng; index++) {
        printf("%c", yytext[index]);
    }
}
%%

int yywrap(){
    return 0;
}

int main() {
    yylex();
    return 0;
}