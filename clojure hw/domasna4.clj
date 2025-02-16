(ns domasna4)

;;;; Group 1

;;; Is argument atomic

(defn atomic? "Returns true if X is not a collection, otherwise false." [X] (not (coll? X)))

;;; Is member of list

(defn member? "Checks if X is a member of list L." [X L]
  (cond
    (empty? L) false
    (= X (first L)) true
    :else (member? X (rest L))))

;;; Number of elements in bottom level of multi-level list

(defn my-count "Counts the number of elements at the bottom level of a multi-level list L." [L]
  (if
    (empty? L) 0
    (+ (my-count (rest L)) 1)))

;;; Append two lists

(defn append "Appends list L2 to the end of list L1." [L1 L2]
  (if
    (empty? L1) L2
    (cons (first L1) (append (rest L1) L2))))

;;; Element pairs in two lists

(defn zip "Pairs elements from lists L1 and L2 into a list of two-element lists." [L1 L2]
  (if
    (or (empty? L1) (empty? L2)) nil
    (cons (list (first L1) (first L2)) (zip (rest L1) (rest L2)))))

;;; Look up by key in list of (key, element) pair lists

(defn lookup "Finds the value associated with Key in a list of key-value pairs L.
  Returns nil if the key is not found." [Key L]
  (cond
    (empty? L) nil
    (= Key (first (first L))) (second (first L))
    :else (lookup Key (rest L))))

;;; Combine into sorted list two sorted lists

(defn my-merge "Merges two sorted lists L1 and L2 into a single sorted list." [L1 L2]
  (cond
    (empty? L1) L2
    (empty? L2) L1
    (< (first L1) (first L2)) (cons (first L1) (my-merge (rest L1) L2))
    :else (cons (first L2) (my-merge L1 (rest L2)))))

;;; Count number of atomic elements in multi-level list

(defn count-all "Counts the number of atomic elements in a multi-level list L."[L]
  (cond
    (empty? L) 0
    (list? (first L)) (+ (count-all (first L)) (count-all (rest L)))
    :else (+ 1 (count-all (rest L)))))

;;; Drop first N elements (if N is equal or greater to list length, return ())

(defn my-drop "Drops the first N elements from list L.
  If N is greater than or equal to the length of L, returns an empty list." [N L]
  (cond
    (empty? L) ()
    (== N 0) L
    :else (my-drop (- N 1) (rest L))))

;;; Return a list of the first N elements of a list

(defn my-take "Returns a list containing the first N elements of list L.
  If N is greater than the length of L, returns the whole list." [N L]
  (cond
    (empty? L) nil
    (== N 0) nil
    :else (cons (first L) (my-take (- N 1) (rest L)))))

;;; Reverse elements in list

(defn my-reverse "Reverses the elements in list L." [L]
  (if
    (empty? L) ()
    (concat (my-reverse (rest L)) (list (first L)))))

;;; Remove duplicates in list (order doesn't matter)

(defn remove-duplicates "Removes duplicate elements from list L while maintaining order." [L]
  (letfn
    [(dupes-help [L]
       (cond
         (empty? L) nil
         (member? (first L) (rest L)) (dupes-help (rest L))
         :else (cons (first L) (dupes-help (rest L)))))]
    (reverse (dupes-help (reverse L)))))

;;; Flatten first level of multi-level list

(defn my-flatten "Flattens only the first level of a multi-level list L." [L]
  (cond
    (empty? L) '()
    (list? (first L)) (append (first L) (my-flatten (rest L)))
    :else (cons (first L) (my-flatten (rest L)))))

;;;; Group 2

;;; Every number divisable by 5 or has atleast one 5 digit to be 
;;; transformed to buzz using map

(defn buzz "Transforms numbers in list L to :buzz if they are divisible by 5 
  or contain the digit 5, otherwise keeps them unchanged." [L]
  (map #(let [chars (seq (str %))]
          (cond
            (member? \5 chars) :buzz
            (= \0 (last chars)) :buzz
            :else %))
       L))

;;; Divisors of number

(defn divisors-of "Returns a list of divisors of N, excluding 1 and N itself." [N]
  (filter #(== 0 (mod N %)) (range 2 N)))

;;; Longest string (found first) in list

(defn longest "Finds the longest string in a list L. 
  If multiple strings have the same maximum length, returns the first found." [L]
  (reduce (fn [X Max]
            (if
              (> (count X) (count Max)) X
              Max))
          L))

;;;; Group 3

(defn my-map "Applies function F to each element of list L, returning a new list." [F L]
  (if
    (empty? L) nil
    (cons (F (first L)) (my-map F (rest L)))))

(defn my-filter "Returns a list of elements from L that satisfy predicate Pred." [Pred L]
  (cond
    (empty? L) nil
    (Pred (first L)) (cons (first L) (my-filter Pred (rest L)))
    :else (my-filter Pred (rest L))))

(defn my-reduce "Reduces list L using function F and an initial Value.
  If Value is nil, uses the first element of L as the initial value." [F Value L]
  (cond
    (empty? L) Value
    (nil? Value) (my-reduce F (first L) (rest L))
    :else (my-reduce F (F Value (first L)) (rest L))))

(defn my-flat-map "Applies function F to each element of list L and flattens the result." [F L]
  (cond
    (empty? L) nil
    :else (append (F (first L)) (my-flat-map F (rest L)))))
