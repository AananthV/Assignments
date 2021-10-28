/*
    LEX Code to Read a Paragraph and count number of words
    with a vovel and the letter 't'
*/

%{
    int count = 0;
%}

/*
    Either vovel followed by t or t followed by vovel.
*/


id [a-zA-Z0-9]*
vowel [aeiouAEIOU]
t [tT]

%%
{id}{vowel}{id}{t}{id} | 
{id}{t}{id}{vowel}{id} {
    printf("\nWord: %s\tCount: %d", yytext, ++count);
}

{id} {/* Match other words */}

\  {/* Match spaces */}
%%

int yywrap(){}

int main() {
    yylex();
    printf("The number of words is %d", count);
    return 0;
}