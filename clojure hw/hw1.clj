(ns hw1)

;;;; Group 1

;;; Is argument atomic

(defn atomic? [X] (not (coll? X)))

;;; Is member of list

(defn member? [X L]
  (cond
    (empty? L) false
    (= X (first L)) true
    :else (member? X (rest L))))

;;; Number of elements in bottom level of multi-level list

(defn my-count [L]
  (if
    (empty? L) 0
    (+ (my-count (rest L)) 1)))

;;; Append two lists

(defn append [L1 L2]
  (if
    (empty? L1) L2
    (cons (first L1) (append (rest L1) L2))))

;;; Element pairs in two lists

(defn zip [L1 L2]
  (if
    (or (empty? L1) (empty? L2)) nil
    (cons (list (first L1) (first L2)) (zip (rest L1) (rest L2)))))

;;; Look up by key in list of (key, element) pair lists

(defn lookup [Key L]
  (cond
    (empty? L) nil
    (= Key (first (first L))) (second (first L))
    :else (lookup Key (rest L))))

;;; Combine into sorted list two sorted lists

(defn my-merge [L1 L2]
  (cond
    (empty? L1) L2
    (empty? L2) L1
    (< (first L1) (first L2)) (cons (first L1) (my-merge (rest L1) L2))
    :else (cons (first L2) (my-merge L1 (rest L2)))))

;;; Count number of atomic elements in multi-level list

(defn count-all [L]
  (cond
    (empty? L) 0
    (list? (first L)) (+ (count-all (first L)) (count-all (rest L)))
    :else (+ 1 (count-all (rest L)))))

;;; Drop first N elements (if N is equal or greater to list length, return ())

(defn my-drop [N L]
  (cond
    (empty? L) ()
    (== N 0) L
    :else (my-drop (- N 1) (rest L))))

;;; Return a list of the first N elements of a list

(defn my-take [N L]
  (cond
    (empty? L) nil
    (== N 0) nil
    :else (cons (first L) (my-take (- N 1) (rest L)))))

;;; Reverse elements in list

(defn my-reverse [L]
  (if
    (empty? L) ()
    (concat (my-reverse (rest L)) (list (first L)))))

;;; Remove duplicates in list (order doesn't matter)

(defn remove-duplicates [L]
  (letfn
    [(dupes-help [L]
       (cond
         (empty? L) nil
         (member? (first L) (rest L)) (dupes-help (rest L))
         :else (cons (first L) (dupes-help (rest L)))))]
    (reverse (dupes-help (reverse L)))))

;;; Flatten first level of multi-level list

(defn my-flatten [L]
  (cond
    (empty? L) '()
    (list? (first L)) (append (first L) (my-flatten (rest L)))
    :else (cons (first L) (my-flatten (rest L)))))

;;;; Group 2

;;; Every number divisable by 5 or has atleast one 5 digit to be 
;;; transformed to buzz using map

(defn buzz [L]
  (map #(let [chars (seq (str %))]
          (cond
            (member? \5 chars) :buzz
            (= \0 (last chars)) :buzz
            :else %))
       L))

;;; Divisors of number

(defn divisors-of [N]
  (filter #(== 0 (mod N %)) (range 2 N)))

;;; Longest string (found first) in list

(defn longest [L]
  (reduce (fn [X Max]
            (if
              (> (count X) (count Max)) X
              Max))
          L))

;;;; Group 3

(defn my-map [F L]
  (if
    (empty? L) nil
    (cons (F (first L)) (my-map F (rest L)))))

(defn my-filter [Pred L]
  (cond
    (empty? L) nil
    (Pred (first L)) (cons (first L) (my-filter Pred (rest L)))
    :else (my-filter Pred (rest L))))

(defn my-reduce [F Value L]
  (cond
    (empty? L) Value
    (nil? Value) (my-reduce F (first L) (rest L))
    :else (my-reduce F (F Value (first L)) (rest L))))

(defn my-flat-map [F L]
  (cond
    (empty? L) nil
    :else (append (F (first L)) (my-flat-map F (rest L)))))
