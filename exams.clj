;;; 1 Lazy-seq for N to 1

(defn count-down-lazy [N]
  (lazy-seq (cons N (count-down-lazy (dec N)))))

(println (take 5 (count-down-lazy 5)))

;; If lazy-seq isn't required

(defn count-down [N]
  (reverse (range 1 (inc N))))

;;; 2 Return a map of squared elements using count-down 50

;; If just a simple map function is required

(defn map-map [N]
  (map #(* % %) (count-down N)))

(println (map-map 5))

;; If each element is supposed to be the key and it squared the value

(defn map-N [N]
  (let [elements (count-down N)]
    (zipmap elements (map #(* % %) elements))))

(println (map-N 5))

;;; 3 Is number divisible by 10

(defn divisible-10? [N]
  (== (mod N 10) 0))

(println (divisible-10? 20) (divisible-10? 1))

;;; 4 With count-down and divisible-10 return all numbers < 150 and their sum

(defn numbers [N]
  (let [els (filter #(divisible-10? %) (count-down N))]
    (concat els (list (reduce + els)))))

(println (numbers 150))

;;; 5 Insert atom S in list in position K

(defn insert-atom [S L K]
  (letfn
    [(insert-help [S L K N]
       (cond
         (empty? L) nil
         (== N K) (cons S (insert-help S L K (inc K)))
         (list? (first L)) (cons (insert-help S (first L) K 0) (insert-help S (rest L) K (inc N)))
         :else (cons (first L) (insert-help S (rest L) K (inc N)))))]
    (insert-help S L K 0)))

(println (insert-atom 'X '(0 1 2 3 (0 (0 1 2) 2)) 2))
