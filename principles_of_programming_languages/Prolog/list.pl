size([], 0).
size([H | T], N):-
  size(T, N1),
  N is 1 + N1.

without_last([_], []).
without_last([X|T], [X|WithoutLast]):-
  without_last(T, WithoutLast).

middle([], []).
middle([X], X).
middle([H | T], X):-
  without_last(T, T1),
  middle(T1, X).

sumlist([], 0).
sumlist([H | T], N):-
  sumlist(T, N1),
  N is H + N1.

average([], 0).
average([H | T], N):-
  size([H | T], NUM),
  sumlist([H | T], SUM),
  N is SUM / NUM.
