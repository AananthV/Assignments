(defun fib (n)
  (let (
      (a 0)
      (b 1)
    )
    (do
      (
        (i 0 (+ i 1))
      )
      (
        (>= i n) a
      )
      (setq b (+ a b))
      (setq a (- b a))
    )
  )
)
