(defun FACT (n)
  (if (< n 2)
    1
    (* n (fact (- n 1)))
  )
)

(defun factlist (list)
	(if (null list)
		NIL
		(append (LIST (FACT (CAR list))) (FACTLIST (CDR list)))
  )
)

(defun factorial(n)
	(do ((i 1 (+ i 1))
		   (fact 1 (* fact i)))
		((> i n) fact)
	)
)
