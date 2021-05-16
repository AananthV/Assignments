collect_to(0, []).
collect_to(N, [N | T]):-
  N1 is N - 1,
  collect_to(N1, T).
