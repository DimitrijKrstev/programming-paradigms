(ns poker-test
  (:require
   [clojure.test :refer [deftest is run-tests testing]]
   [poker :refer :all]))

(deftest test-suit
  (is (= (suit "2H") \H))
  (is (= (suit "KD") \D))
  (is (= (suit "AS") \S))
  (is (= (suit "TC") \C))
  (is (= (suit "7H") \H)))

(deftest test-rank
  (is (= (rank "2H") 2))
  (is (= (rank "TD") 10))
  (is (= (rank "JH") 11))
  (is (= (rank "QH") 12))
  (is (= (rank "AS") 14)))

(deftest test-pair?
  (is (pair? ["2H" "2D" "5S" "9C" "KD"]))
  (is (pair? ["TH" "TD" "7S" "QC" "8D"]))
  (is (not (pair? ["3H" "4D" "5S" "6C" "7D"])))
  (is (not (pair? ["9H" "9D" "9S" "9C" "KD"])))
  (is (not (pair? ["AH" "AD" "5S" "5C" "KD"]))))

(deftest test-three-of-a-kind?
  (is (three-of-a-kind? ["2H" "2D" "2S" "9C" "KD"]))
  (is (three-of-a-kind? ["KH" "KD" "KS" "QC" "8D"]))
  (is (not (three-of-a-kind? ["3H" "4D" "5S" "6C" "7D"])))
  (is (not (three-of-a-kind? ["9H" "9D" "9S" "9C" "KD"]))) ;; This is four-of-a-kind
  (is (not (three-of-a-kind? ["AH" "AD" "AS" "5C" "5D"])))) ;; This is full house

(deftest test-four-of-a-kind?
  (is (four-of-a-kind? ["2H" "2D" "2S" "2C" "KD"]))
  (is (four-of-a-kind? ["KH" "KD" "KS" "KC" "8D"]))
  (is (not (four-of-a-kind? ["3H" "4D" "5S" "6C" "7D"])))
  (is (not (four-of-a-kind? ["AH" "AD" "AS" "5C" "5D"]))) ;; This is full house
  (is (four-of-a-kind? ["AH" "AD" "AS" "AC" "KD"]))) ;; Should return true but test against different suit

(deftest test-flush?
  (is (flush? ["2H" "5H" "9H" "JH" "KH"]))
  (is (flush? ["3D" "6D" "8D" "TD" "QD"]))
  (is (not (flush? ["2H" "3D" "5H" "9C" "KH"])))
  (is (not (flush? ["2H" "3H" "4H" "5H" "6H"]))) ;; This is straight-flush
  (is (flush? ["AH" "JH" "KH" "7H" "5H"])))

(deftest test-full-house?
  (is (full-house? ["2H" "2D" "2S" "3C" "3D"]))
  (is (full-house? ["KH" "KD" "KS" "QC" "QD"]))
  (is (not (full-house? ["3H" "4D" "5S" "6C" "7D"])))
  (is (not (full-house? ["9H" "9D" "9S" "9C" "KD"]))) ;; Four-of-a-kind, not full-house
  (is (full-house? ["AH" "AD" "AS" "KC" "KD"])))

(deftest test-two-pairs?
  (is (two-pairs? ["2H" "2D" "3S" "3C" "KD"]))
  (is (two-pairs? ["KH" "KD" "JS" "JC" "8D"]))
  (is (not (two-pairs? ["3H" "4D" "5S" "6C" "7D"])))
  (is (not (two-pairs? ["9H" "9D" "9S" "9C" "KD"]))) ;; Four-of-a-kind
  (is (not (two-pairs? ["AH" "AD" "AS" "KC" "KD"])))) ;; Full house

(deftest test-straight?
  (is (straight? ["2H" "3S" "4C" "5D" "6H"]))
  (is (straight? ["TH" "JH" "QC" "KD" "AS"]))
  (is (not (straight? ["3H" "4D" "6S" "7C" "9D"])))
  (is (straight? ["5H" "6D" "7S" "8C" "9D"]))
  (is (straight? ["AH" "2D" "3S" "4C" "5D"]))) ;; Ace as low

(deftest test-straight-flush?
  (is (straight-flush? ["2H" "3H" "4H" "5H" "6H"]))
  (is (straight-flush? ["TH" "JH" "QH" "KH" "AH"]))
  (is (not (straight-flush? ["3H" "4H" "6H" "7H" "9H"])))
  (is (not (straight-flush? ["5H" "6H" "7H" "8H" "9C"]))) ;; Not all suits the same
  (is (straight-flush? ["AH" "2H" "3H" "4H" "5H"]))) ;; Ace-low straight flush

(deftest test-value
  (is (= (value ["2H" "3S" "6C" "5D" "4D"]) 4))  ;; Straight
  (is (= (value ["5H" "5D" "6C" "7D" "7S"]) 2))  ;; Two pairs
  (is (= (value ["5H" "5D" "5C" "7D" "7S"]) 6))  ;; Full house
  (is (= (value ["5H" "5H" "AH" "KH" "2H"]) 5))  ;; Flush
  (is (= (value ["KH" "KD" "KC" "KS" "7S"]) 7))  ;; Four of a kind
  (is (= (value ["9H" "TH" "JH" "QH" "KH"]) 8))  ;; Straight flush
  (is (= (value ["4H" "4D" "4C" "9S" "2S"]) 3))  ;; Three of a kind
  (is (= (value ["5H" "5D" "3C" "7D" "9S"]) 1))) ;; Pair

(deftest test-kickers
  (is (= (kicker ["2H" "3S" "6C" "5D" "4D"]) '(6 5 4 3 2)))  ;; Straight
  (is (= (kicker ["5H" "AD" "5C" "7D" "AS"]) '(14 5 7)))     ;; Two pairs
  (is (= (kicker ["5H" "5D" "5C" "7D" "7S"]) '(5 7)))        ;; Full house
  (is (= (kicker ["KH" "KD" "KC" "KS" "7S"]) '(13 7)))       ;; Four of a kind
  (is (= (kicker ["2H" "3H" "4H" "5H" "6H"]) '(6 5 4 3 2)))) ;; Straight flush

(deftest test-higher-kicker?
  (is (higher-kicker? '(8 5 9) '(8 3 7)))
  (is (not (higher-kicker? '(8 5 9) '(8 7 3))))
  (is (not (higher-kicker? '(10 8 5) '(10 8 5))))  ;; Equal hands
  (is (higher-kicker? '(14 13 4) '(14 12 7)))  ;; First hand wins (higher second card)
  (is (not (higher-kicker? '(9 8 6) '(9 8 7)))))  ;; Second hand wins (higher third card)

(deftest test-beats?
  (is (beats? ["AH" "AS" "AD" "AC" "2H"] ["KH" "KS" "KD" "KC" "2S"])) ;; Aces beat Kings
  (is (not (beats? ["KH" "3H" "AH" "5H" "6H"] ["KH" "KS" "KD" "KC" "2S"]))) ;; Four of a kind beats flush
  (is (beats? ["5H" "6H" "7H" "8H" "9H"] ["4H" "5H" "6H" "7H" "8H"])) ;; Higher straight flush wins
  (is (not (beats? ["3H" "3S" "3D" "9C" "2H"] ["4H" "4S" "4D" "8C" "2S"]))) ;; Three of a kind of 4s beats three of a kind of 3s
  (is (beats? ["KH" "KD" "5C" "5D" "7S"] ["KH" "KD" "3C" "3D" "9S"]))) ;; Two pairs: 5s and Ks beat 3s and Ks

(deftest test-winning-hand
  (is (= (winning-hand ["AH" "AS" "AD" "AC" "2H"] ["KH" "KS" "KD" "KC" "2S"])
         [["AH" "AS" "AD" "AC" "2H"]]))  ;; Four Aces win
  (is (= (winning-hand ["5H" "6H" "7H" "8H" "9H"] ["4H" "5H" "6H" "7H" "8H"])
         [["5H" "6H" "7H" "8H" "9H"]]))  ;; Higher straight flush wins
  (is (= (winning-hand ["2H" "3H" "4H" "5H" "6H"] ["2D" "3D" "4D" "5D" "6D"])
         [["2H" "3H" "4H" "5H" "6H"] ["2D" "3D" "4D" "5D" "6D"]])) ;; Tie case
  (is (= (winning-hand ["9H" "9D" "9S" "KH" "2C"] ["9C" "9S" "9D" "KH" "3C"])
         [["9C" "9S" "9D" "KH" "3C"]])) ;; Three of a kind tiebreaker (3C > 2C)
  (is (= (winning-hand ["AH" "KH" "QH" "JH" "TH"] ["AD" "KD" "QD" "JD" "TD"])
         [["AH" "KH" "QH" "JH" "TH"] ["AD" "KD" "QD" "JD" "TD"]]))) ;; Tie with two Royal Flushes

(run-tests)
