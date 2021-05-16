gcd(A, B, N):-
  (A = 0, N is B);
  (A > B, A1 is A mod B, gcd(A1, B, N1), N is N1);
  (gcd(B, A, N1), N is N1).
