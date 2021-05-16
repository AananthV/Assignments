min2(X, Y, N):-
  (X > Y, N is Y);
  (Y >= X, N is X).
