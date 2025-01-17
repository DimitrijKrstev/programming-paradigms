;;;; Aud 1 Clojure Introduction

;;; 0 Form (10 (20 30)) with cons and list

(def X 10)
(def Y 20)
(def Z 30)

(cons X (cons (cons Y (cons Z nil)) nil))
(cons 'x (cons (cons 'y (cons 'z nil)) nil))

(list X (list Y Z))
(list 'x (list 'y 'z))

;; Alternative without vars
(cons 10 (cons (cons 20 (cons 30 nil)) nil))
(list 10 (list 20 30))

;;; 1 Form ((А (B)) B C) with cons and list

(println (cons (cons 'A (cons (cons 'B nil) nil)) (cons 'B (cons 'C nil))))
(println (list (list 'A (list 'B)) 'B 'C))

;;; 2 Take two lists and return a list with their heads

(defn heads [L1 L2]
  (list (first L1) (first L2)))

(println (heads '(1 2 3) '(2 3)))

;;; 3 Replace element at 3rd position in list

(defn el_at_3rd_rest [L Num]
  (list (first L) (second L) Num (first (rest (rest (rest L))))))

;; Alternative with nth
(defn el_at_3rd [L Num]
  (list (first L) (second L) Num (nth L 3)))

(println (el_at_3rd_rest '(1 2 5 4) 3))
(println (el_at_3rd '(1 2 5 4) 3))

;;; 4 Product of 1st 3rd and 5th element

(defn odd_product [L]
  (* (nth L 0) (nth L 2) (nth L 4)))

(println (odd_product '(1 0 2 0 3)))

;;; 5 & 6 Uneven palindrome 

(defn palindrome [L]
  (concat L (rest (reverse L))))

(println (palindrome '(1 2 3 4)))
