/*
    LEX Code to Convert an infix expression 
    to a postfix expression.
    Eg:
        Input:
            (a*(b+c)-d)*(e+f)
        Output:
            abc+*d-ef+*

    Compile with g++ for std::stack
*/


/* Declare stack. */
%{
    #include<stack>

    std::stack<char> s;

    int get_priority(char op) {
        switch(op) {
            case '(':
            case ')':
                return 1;
            case '+':
            case '-':
                return 2;
            case '*':
            case '/':
                return 3;
        }
        return 0;
    }

    void handle_operator(char op) {
        if (get_priority(op) == 1) {
            if (op == ')') {
                while (s.top() != '(') {
                    printf("%c", s.top());
                    s.pop();
                }
                s.pop();
            } else {
                s.push('(');
            }
        } else {
            while (!s.empty() && get_priority(s.top()) > get_priority(op)) {
                printf("%c", s.top());
                s.pop();
            }
            s.push(op);
        }
    }

    void empty_stack() {
        while (!s.empty()) {
            printf("%c", s.top());
            s.pop();
        }
    }
%}

%%
[()+\-*/] { handle_operator(yytext[0]); }

\n { empty_stack(); }
%%

int yywrap(){
    return 0;
}

int main() {
    yylex();
    return 0;
}