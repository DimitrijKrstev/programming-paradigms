(ns poker)

;;;; Poker

;;; 1 Return card suit

(defn suit [Card]
  (second Card))

;;; 2 Return card rank

(def face-card-map {"T" 10 "J" 11 "Q" 12 "K" 13 "A" 14})

(defn rank [Card]
  (if (Character/isDigit (first Card))
    (Integer/valueOf (str (first Card)))
    (face-card-map (str (first Card))))) ; either make this string or escape in map (\T)

;;; 3 Is hand pair

(defn freq-no-suit [Hand]
  (frequencies (map rank Hand)))

(defn pair? [Hand]
  (== 4 (count (freq-no-suit Hand)))) ; In hand of 5 cards, frequencies has 4 counts only if 2 are same

;;; 4 Three of a kind

(defn of-a-kind [Hand Count N]
  (let [counts (freq-no-suit Hand)]
    (and
      (== Count (count counts))
      (true? (some #(== N (val %)) counts))))) ; true? because some returns nil not bool

(defn three-of-a-kind? [Hand]
  (of-a-kind Hand 3 3)) ; Only 3 groups, one is a group of 3

;;; 5 Four of a kind

(defn four-of-a-kind? [Hand]
  (of-a-kind Hand 2 4)) ; Only 2 groups, one is a group of 4

;;; 6 Flush (same suit different rank, cannot be ordered)

(defn ordered-ranks? [Ranks]
  (every? #(= (second %) (inc (first %)))
          (map vector Ranks (into [] (rest Ranks)))))

(defn suits-match? [Hand]
  ;; Alternatives:
  ; (apply = (map suit Hand))
  ; (not (some #(not= (suit (first Hand)) (suit %)) Hand))
  (every? #(= (suit (first Hand)) (suit %)) Hand))

(defn flush? [Hand]
  (let [ranks (sort (map rank Hand))]
    (and
      (suits-match? Hand)
      (not (ordered-ranks? ranks)))))

;;; 7 Full house (3 + 2 pair)

(defn full-house? [Hand]
  (of-a-kind Hand 2 3)) ; Only 2 groups, one is a group of 3

;;; 8 Two pairs (2 + 2 pair)

(defn two-pairs? [Hand]
  (and (of-a-kind Hand 3 1) (of-a-kind Hand 3 2))) ; 3 groups, one of 1, one of 2

;;; 9 Straight (All ranks are in an ascending order, Ace can be 14 or 1)

(defn straight? [Hand]
  (let [ranks (sort (map rank Hand))]
    (or
      (ordered-ranks? ranks)
      (and
        (= (last ranks) 14)
        (ordered-ranks? (reverse (concat (rest (reverse ranks)) [1]))))))) ; replace Ace with 1

  ;;; 10 Straight Flush (both)

(defn straight-flush? [Hand]
  (and (straight? Hand) (suits-match? Hand)))

;;; 11 Value (return int depending on hand)

(defn value [Hand]
  (cond
    (straight-flush? Hand) 8
    (four-of-a-kind? Hand) 7
    (full-house? Hand) 6
    (flush? Hand) 5
    (straight? Hand) 4
    (three-of-a-kind? Hand) 3
    (two-pairs? Hand) 2
    (pair? Hand) 1
    :else 0))

;;; 12 Kicker (hand value in order)

(defn desc-sort
  ([L SortFn] (reverse (SortFn L)))
  ([L] (reverse (sort L))))

(defn get-keys-with-values [L X]
  (flatten ; so it doesn't return list of lists
    (for [Val X]
      (filter #(= Val (get L %)) (keys L)))))

;; sorts by anonymous function that takes mapentry as arg, sorts by value first, then key

(defn sort-by-keys [L]
  (sort-by (fn [[key value]] [value key]) L))

(defn get-desc-sorted-keys-by-val [Hand]
  (keys (desc-sort (freq-no-suit Hand) sort-by-keys)))

(defn kicker [Hand]
  (cond
    (or (straight? Hand) (flush? Hand) (straight-flush? Hand)) (desc-sort (map #(rank %) Hand))
    (two-pairs? Hand) (get-desc-sorted-keys-by-val Hand)
    (full-house? Hand) (get-desc-sorted-keys-by-val Hand)
    (four-of-a-kind? Hand) (get-desc-sorted-keys-by-val Hand)
    (three-of-a-kind? Hand) (get-desc-sorted-keys-by-val Hand)
    (pair? Hand) (get-desc-sorted-keys-by-val Hand)
    :else (desc-sort (map #(rank %) Hand))))

;;; 13 Higher kicker (one element is larger than its counter part first)

(defn higher-kicker? [K1 K2]
  (loop [[x1 & xs1] K1
         [x2 & xs2] K2]
    (cond
      (nil? x1) false
      (> x1 x2) true
      (< x1 x2) false
      :else (recur xs1 xs2))))

;;; 14 Which hand is better (compare hands first then individual cards)

(defn beats? [H1 H2]
  (let [V1 (value H1) V2 (value H2)]
    (if
      (== V1 V2) (higher-kicker? (kicker H1) (kicker H2))
      (> V1 V2))))

;;; 15 Find winning hand/s

;; Iterates through all hands and finds maximum, if tied both are joined to a single list
;; and first element is used for comparison

(defn winning-hand [& Hands]
  (if (empty? Hands)
    nil
    (reduce (fn [winners hand]
              (cond
                (beats? hand (first winners)) [hand]  ; Found a better hand, replace winners
                (beats? (first winners) hand) winners ; Current winners are still the best
                :else (conj winners hand)))           ; If tied, add to winners
            [(first Hands)]
            (rest Hands))))
