(defun l-index (list n)
  (cond ((= (car list) n) 1)
        (t (+ 1 (l-index (cdr list) n)))
  )
)

(defun index (list n)
  (if (memeber n list)
    l-index(list n)
    -1
  )
)
