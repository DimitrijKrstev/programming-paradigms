;;;; Aud 2 Predicates, Relational, Logical and Control functions

;;; 0 Swap first element

(defn swap_first [X L]
  (cons X (rest L))) ; list would return (a (b c))

(println (swap_first 'a '(b c d)))

;;; 1 Distance between two R^2 vectors

(defn dot_distance [[X1 X2], [Y1 Y2]]
  (Math/sqrt (+ (Math/pow (- Y1 X1) 2)
                (Math/pow (- Y2 X2) 2))))

(println (dot_distance [1 2] [4 6])) ; [] is an array while () is a list

;;; 2 Perimiter and Area of Rectangle

(defn rectangle [[X1 X2]]
  (list (+ (* 2 X1) (* 2 X2))
        (* X1 X2)))

(println (rectangle [2 3]))

;;; 3 Double reversed list

(defn double_reverse [L]
  (concat (reverse L) (reverse L)))

(println (double_reverse '(a b c)))

;;; 4 Add element at end of list

(defn end_of_list [X L]
  (concat L (cons X nil)))

;; Alternative with cons (reverse is used because the first arg of cons
;; isn't expanded so the result would be ((a b c) x) 

(defn end_of_list_rev [X L]
  (reverse (cons X (reverse L))))

(println (end_of_list 'x '(a b c)))
(println (end_of_list_rev 'x '(a b c)))

;;; 5 Rotate array by 1 to the right

(defn rotate_1 [L]
  (cons (last L) (reverse (rest (reverse L)))))

(println (rotate_1 '(a b c d)))

;;; 6 Is first element a symbol, second a number, third a list

(defn is_true [S N L]
  (list (symbol? S) (number? N) (list? L)))

(println (is_true 'symbol 0 '(1 2 3)))

;;; Control functions examples

(println (if (= 1 1) '(true) '(false)))

(println (cond
           (= 1 3) '(this is true)
           (= 1 2) '(this is true)
           :else '(neither is true)))

;;; 7 Is Palindrome

(defn is_palindrome [L]
  (= L (reverse L)))

(println (is_palindrome '(a b b a)))
(println (is_palindrome '(a b c)))

;;; 8 Last element of list if list, symbol if symbol, nil if empty list

(defn return_val [L]
  (cond
    (list? L) (last L)
    (symbol? L) L
    (empty? L) nil))

(println (return_val ()))

;;; 9 If symbol return symbol in list, if list return it, if empty nil

(defn return_list [L]
  (cond
    (symbol? L) (cons L nil)
    (list? L) L
    (empty? L) nil))

(println (return_list '(a)))

;;; 10 Divide two numbers if they are numbers and non-zero, otherwise nil

(defn divide [X1 X2]
  (if (and (number? X1) (number? X2) (not (== X2 0))) (/ X1 X2) nil))

(println (divide 6 3))
