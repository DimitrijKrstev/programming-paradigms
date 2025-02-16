(ns boja)

(defn is-valid? [Grid Row]
  (let [cols (apply map vector Grid)]  ; Transpose to get columns 
    (every? #(apply distinct? (take (inc Row) %)) cols)))  ; Include current row

(defn rotate-row [Grid Row]
  (let [row (nth Grid Row)]
    (assoc Grid Row (vec (concat (rest row) [(first row)])))))

(defn solve
  ([Grid] (solve Grid 0 0))
  ([Grid Row Rotations]
   (cond
     ;; Solution found
     (= Row (count Grid)) Grid

     ;; If all rotations for the current row are exhausted, backtrack
     (>= Rotations (count (nth Grid Row)))
     (if (zero? Row) nil  ; No solution if backtracked to very first Row
         (solve (assoc Grid Row (nth Grid Row)) (dec Row) (inc Rotations))) ; Reset row and backtrack

     ;; If valid, move to the next row
     (is-valid? Grid Row) (solve Grid (inc Row) 0)

     ;; Rotate and retry
     :else
     (let [new-grid (rotate-row Grid Row)]
       (if (is-valid? new-grid Row)
         (solve new-grid (inc Row) 0)
         (solve new-grid Row (inc Rotations)))))))

;;;; BONUS visualize

;;; Seesaw visualization

(require '[boja-visual :refer [show-grid]])

(show-grid "Unsolved grid" [[1 2 3 4] [3 4 1 2] [3 4 1 2] [4 1 2 3]])
(show-grid "Solved grid" (solve [[1 2 3 4] [3 4 1 2] [3 4 1 2] [4 1 2 3]]))
