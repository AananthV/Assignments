/*
    Program to convert a for construct to a do..while construct.
    Assumptions:
        1. For only has one initialisation and increment.
        2. For always has a body. {}
        3. No nested loop.
    Eg:
        Input:
            for(i=0;i<n;i++) {
                printf("%d ", arr[i]);
            }
        Output:
            i=0;
            do {
                printf("%d ", arr[i]);
                i++;
            } while (i<n);
*/

inc "++"|"--"
rop "<"|">"|"<="|">="|"=="|"!="
id [a-zA-Z][a-zA-Z0-9]*
no [0-9]*
pp [\n\t" "]

%{
    char condition[32], increment[32];
%}

%%
for\(({id}=({no}|{id}))?\;({id}{rop}({id}|{no}))?\;({id}{inc})?\) {
    int index = 4;
    while(yytext[index++] != ';') {
        printf("%c", yytext[index - 1]);
    }
    printf(";\ndo");

    int begin = index;
    while(yytext[index++] != ';');

    strncpy(condition, yytext + begin, index - begin - 1);

    begin = index;
    while(yytext[index++] != ')');
    strncpy(increment, yytext + begin, index - begin - 1);
}

\} { printf("%s;\n} while(%s);", increment, condition); }
%%

int yywrap(){
    return 0;
}

int main() {
    yylex();
    return 0;
}
