;;;; Aud 3 Recursion

;;; 0.1 Factorial

(defn factorial [X]
  (if (== X 0) 1 (* X (factorial (- X 1)))))

(println (factorial 4))

;;; 1 Factorial sum

(defn factorial_sum [X]
  (if (== X 0) 1 (+ X (factorial_sum (- X 1)))))

;;; 0.2 Factorial position with tail recursion

(defn fac_pos [N Predecessor Current]
  (if (== N 2) ; 2 is base case because 1, 1 are first two elements
    Current
    (fac_pos (- N 1) Current (+ Predecessor Current))))

(defn factorial_pos [N]
  (fac_pos N 1 1))

(println (factorial_pos 6))

;;; 2 M^N power tail recursion

(defn power [M N]
  (letfn
    [(power-helper [M N Acc]
       (if (== N 0)
         Acc
         (power-helper M (- N 1) (* Acc M))))]
    (power-helper M N 1)))

(println (power 3 4))

;;; 3 Sum of range (1, M)^N tail recursion

(defn sum_range_power [M N]
  (letfn
    [(sum_range_help [M N Acc]
       (if (== M 1)
         Acc
         (sum_range_help (- M 1) N (+ Acc (power M N)))))]
    (sum_range_help M N 1)))

(println (sum_range_power 3 2))

;;; 0.3 Sum of elements in a list

(defn sum_els [L]
  (if (empty? L)
    0
    (+ (sum_els (rest L)) (first L))))

(println (sum_els '(1 2 3 4)))

;;; 0.4 Append two lists

(defn append_lists [L1 L2]
  (if (empty? L1)
    L2
    (cons (first L1) (append_lists (rest L1) L2))))

(println (append_lists '(1 2 3) '(4 5)))

;;; 4 Number of symbols in the first level of list

(defn num_symbols [L]
  (cond
    (empty? L) 0
    (list? (first L)) (num_symbols (rest L))
    (symbol? (first L)) (+ 1 (num_symbols (rest L)))
    :else (num_symbols (rest L))))

(println (num_symbols '(a (b c) d (e (f)))))

;;; 5 Is element of list in first level

(defn el_in_first [X L]
  (cond
    (empty? L) false
    (= X (first L)) true
    :else (el_in_first X (rest L))))

(println (el_in_first 3 '(1 2 (3))))

;;; 6 Reduce all elements in list by 2 and append their sum

(defn reduce_two [L]
  (letfn
    [(reduce_help [L Acc]
       (if (empty? L)
         (cons Acc nil) ; Number is illegal argument
         (cons (- (first L) 2) (reduce_help (rest L) (+ (- (first L) 2) Acc)))))]
    (reduce_help L 0)))

(println (reduce_two '(3 4 5)))

;;; 7 Extract even numbers from list to new list

(defn extract_even [L]
  (if (empty? L)
    nil
    (if (even? (first L))
      (cons (first L) (extract_even (rest L)))
      (extract_even (rest L)))))

(println (extract_even '(1 2 3 4 5 6 7 8 9)))
(println (extract_even ()))

;;; 8 Is number prime

(defn prime? [X]
  (letfn
    [(prime_help [X N]
       (cond
         (== N 1) true
         (== (mod X N) 0) false
         :else (prime_help X (- N 1))))]
    (prime_help X (- X 1))))

(println (prime? 11))
(println (prime? 12))
