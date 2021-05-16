fibonacci(0, 1).
fibonacci(1, 1).

fibonacci(X, N):-
  X > 1,
  X1 is X - 1,
  X2 is X - 2,
  fibonacci(X1, N1),
  fibonacci(X2, N2),
  N is N1 + N2.
