;;;; Aud 4 Recursion 2. Sequences

;;; 1 Number of sublists in list

(defn num_sublists [L]
  (cond
    (empty? L) 0
    (list? (first L)) (+ 1 (+ (num_sublists (first L)) (num_sublists (rest L))))
    :else (num_sublists (rest L))))

(println (num_sublists '(a (3 (4)))))

;;; 2 New list of numbers from multi-level list

(defn numbers_list [L]
  (cond
    (empty? L) nil
    (number? (first L)) (cons (first L) (numbers_list (rest L)))
    ; Since first element is a list, use concat so result is a single level list
    (list? (first L)) (concat (numbers_list (first L)) (numbers_list (rest L)))
    :else (numbers_list (rest L))))

(println (numbers_list '(3 (1 2) 4 c)))

;;; 3 Level multi-level list

(defn level [L]
  (cond
    (empty? L) nil
    (list? (first L)) (concat (level (first L)) (level (rest L)))
    :else (cons (first L) (level (rest L)))))

(println (level '(a b (a (b c)))))

;;; 4 Delete element from multi-level list

(defn del_multi [X L]
  (cond
    (empty? L) nil
    (list? (first L)) (cons (del_multi X (first L)) (del_multi X (rest L)))
    (= X (first L)) (del_multi X (rest L))
    :else (cons (first L) (del_multi X (rest L)))))

(println (del_multi 'a '(a b a)))
(println (del_multi 'a '(a (b a) b a)))
(println (del_multi 'a '(a ((a)) b a)))

;;; 5 Remove N-th element from multi-level list

(defn remove_help [N L Acc]
  (cond
    (empty? L) nil
    (== N Acc) (rest L)
    :else (cons (first L) (remove_help N (rest L) (+ Acc 1)))))

(defn remove_nth [N L]
  (cond
    (empty? L) nil
    (list? (first L)) (cons (remove_nth N (first (remove_help N L 1))) (remove_nth N (rest (remove_help N L 1))))
    :else (cons (first (remove_help N L 1)) (remove_nth N (rest (remove_help N L 1))))))

(println (remove_nth 3 '((1 2 3 4) ((1 3 5) 3 1) (1 2 2 1))))

;;; 6 Append to each list in multi-level list (Original is for pure list of lists)

(defn multi_append [X L]
  (cond
    (empty? L) nil
    (list? (first L)) (cons (cons X (multi_append X (first L))) (multi_append X (rest L)))
    :else (cons (first L) (multi_append X (rest L)))))

(println (multi_append 'a '((1 2 3) ((9)) (a b c))))
(println (multi_append 'a '()))
(println (multi_append 'a '(() (1 2 3))))

;;; 7 Quadratic formula

(defn quadratic [A, B, C]
  (let [Discriminant (- (* B B) (* 4 (* A C)))]
    (cond
      (< Discriminant 0) "Nema reshenie"
      (= Discriminant 0) [(/ (- B) (* 2 A))]
      :else [(/ (+ (- B) (Math/sqrt Discriminant)) (* 2 A))
             (/ (- (- B) (Math/sqrt Discriminant)) (* 2 A))])))

(println (quadratic 1 2 -3))
(println (quadratic 1 2 1))
(println (quadratic 1 1 1))

;;; 8 Append to each vector in multi-level vector (Same as 6)

(defn vectors_append [X L]
  (cond
    (empty? L) nil
    (vector? (first L)) (cons (conj (vectors_append X (first L)) X) (vectors_append X (rest L)))
    :else (cons (first L) (vectors_append X (rest L)))))

(println (vectors_append 'a '[[1 2 3] [[9]] [a b c]]))
(println (vectors_append 'a '[]))
(println (vectors_append 'a '[[] [1 2 3]]))
