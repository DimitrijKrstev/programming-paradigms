(ns boja-visual)

(require '[seesaw.core :as s])
(require '[seesaw.color :as c])
(require '[seesaw.border :as b])

(def colors
  {0  (c/color 255 255 255)  ;; White for empty
   1  (c/color 255 200 200)  ;; Light red
   2  (c/color 200 255 200)  ;; Light green
   3  (c/color 200 200 255)  ;; Light blue
   4  (c/color 255 255 200)  ;; Light yellow
   5  (c/color 255 200 255)  ;; Light pink
   6  (c/color 200 255 255)  ;; Light cyan
   7  (c/color 255 150 100)  ;; Orange
   8  (c/color 150 255 150)  ;; Greenish
   9  (c/color 150 150 255)  ;; Blueish
   10 (c/color 255 100 200)  ;; Magenta
   11 (c/color 100 200 255)  ;; Sky blue
   12 (c/color 255 180 120)  ;; Peach
   13 (c/color 200 200 200)  ;; Gray
   14 (c/color 180 120 255)  ;; Purple
   15 (c/color 100 255 200)  ;; Teal
   16 (c/color 220 100 255)}) ;; Violet

(defn get-color [value]
  (get colors value (c/color 150 150 150)))

(defn grid-cell [value]
  (s/label
    :background (get-color value)
    :opaque? true
    :border (b/line-border :color :black :thickness 1)))

(defn show-grid [Title Grid]
  (let [rows (count Grid)
        cols (count (first Grid))
        cells (for [row (range rows), col (range cols)]
                (grid-cell (get-in Grid [row col])))]
    (s/frame
      :title Title
      :content (s/grid-panel
                 :rows rows
                 :columns cols
                 :items cells)
      :size [500 :by 500]
      :visible? true)))
