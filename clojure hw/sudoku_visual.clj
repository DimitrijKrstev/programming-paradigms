(ns sudoku-visual)

(require '[seesaw.core :as s])
(require '[seesaw.border :as b])

(defn get-border-thickness [row col]
  (let [thick 3   ; Thick for 3x3 grid
        thin 1    ; Thin for regular cell
        bottom (if (= row 8) thick (if (= 0 (mod (inc row) 3)) thick thin))
        right  (if (= col 8) thick (if (= 0 (mod (inc col) 3)) thick thin))
        top    (if (= 0 row) thick thin)
        left   (if (= 0 col) thick thin)]
    [top left bottom right]))

(defn sudoku-cell [value row col]
  (let [[top left bottom right] (get-border-thickness row col)]
    (s/label
      :text (if (zero? value) "" (str value))
      :h-text-position :center
      :v-text-position :center
      :font "Arial-BOLD-20"
      :foreground "black"
      :background "white"
      :opaque? true
      :border (b/line-border :color :black :top top :left left :bottom bottom :right right))))

(defn show-sudoku [Title Grid]
  (let [cells (for [row (range 9), col (range 9)]
                (sudoku-cell (get-in Grid [row col]) row col))]
    (s/frame
      :title Title
      :content (s/grid-panel
                 :rows 9
                 :columns 9
                 :items cells)
      :size [500 :by 500]
      :visible? true)))
