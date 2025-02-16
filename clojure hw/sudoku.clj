(ns sudoku)

;;;; 1 Sudoku solver

;;; Brute-force algorithm for Sudoku solving

(def quadrants [[[0 3] [0 3]] [[0 3] [3 6]] [[0 3] [6 9]]
                [[3 6] [0 3]] [[3 6] [3 6]] [[3 6] [6 9]]
                [[6 9] [0 3]] [[6 9] [3 6]] [[6 9] [6 9]]])

(defn row-contains? [R X]
  (some #(== % X) R))

(defn col-contains? [S X Col]
  (some #(== (nth % Col) X) S))

(defn get-rows [S Q]
  "Returns row indicies by quadrant Q"
  (subvec S
          (first (first (nth quadrants Q)))
          (second (first (nth quadrants Q)))))

(defn get-cols-range [R Q]
  "Returns col indicies by quadrant Q"
  (range (first (second (nth quadrants Q)))
         (second (second (nth quadrants Q)))))

;; Checks the quadrant for existing element by first getting the rows
;; of the quadrant and then checking every element in the particular rows
;; by the column range for that quadrant"

(defn quadrant-contains? [S X Q]
  "Does quadrant Q contain value X in sudoku S"
  (let [rows (get-rows S Q)
        cols-range (get-cols-range rows Q)]
    (some #(col-contains? rows X %) cols-range)))

(defn get-quadrant [Row Col]
  "Returns quadrant Q that contains Row and Col"
  (+ (* (quot Row 3) 3) (quot Col 3))) ; quot is integer division

(defn is-valid? [S Row Col X]
  "Is X valid value for position in Row Col"
  (let [Quad (get-quadrant Row Col)]
    (and (not (row-contains? (nth S Row) X))
         (not (col-contains? S X Col))
         (not (quadrant-contains? S X Quad)))))

;; Loop through every row, return col pos (0-8) of first zero element

(defn find-empty-cell [S]
  "Returns Row and Col of first empty element in sudoku S"
  (loop [row 0]
    (if (< row 9)
      (let [col (some #(when (zero? (nth (nth S row) %)) %) (range 9))]
        (if col
          [row col]
          (recur (inc row))))
      nil)))

;; Find the next empty position (if there is none left we have a solution)
;; when a number in the range 1-9 is valid for that empty position during 
;; the current state continue with the next empty position. If the number  
;; is a bad guess (valid for only current position), backtrack and try next
;; number in range

(defn solve [S]
  (if-let [[row col] (find-empty-cell S)]
    (some
      #(when (is-valid? S row col %)
         (solve (assoc S row (assoc (nth S row) col %))))
      (range 1 10))
    S))

;;;; 2 Transform Sudoku grid by replacing each element with a set

(defn replace-row [R]
  (map #(if (== 0 %)
          (set (range 1 10))
          #{%}) R))

(defn transform [S]
  (map #(replace-row %) S))

;;;; BONUS Visualize

;;; Println visualization

(def sudoku [[5 3 0 0 7 0 0 0 0]
             [6 0 0 1 9 5 0 0 0]
             [0 9 8 0 0 0 0 6 0]
             [8 0 0 0 6 0 0 0 3]
             [4 0 0 8 0 3 0 0 1]
             [7 0 0 0 2 0 0 0 6]
             [0 6 0 0 0 0 2 8 0]
             [0 0 0 4 1 9 0 0 5]
             [0 0 0 0 8 0 0 7 9]])

(defn print-sudoku [S]
  (doseq [Row S] (println Row)))

;;; Seesaw visualization

(require '[sudoku-visual :refer [show-sudoku]])

(show-sudoku "Unsolved sudoku" sudoku)
(show-sudoku "Solved sudoku" (solve sudoku))
