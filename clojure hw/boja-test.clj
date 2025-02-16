(ns boja-test
  (:require
   [boja :refer :all]
   [clojure.test :refer [deftest is run-tests testing]]))

(deftest test-is-valid?
  (testing "Valid matrices at different row depths"
    (is (true? (is-valid? [[1 2] [2 1]] 0)))   ; Always valid for Row 0
    (is (true? (is-valid? [[1 2] [2 1]] 1)))   ; Unique values in each column
    (is (true? (is-valid? [[1 2 3] [3 1 2] [2 3 1]] 2)))) ; Fully valid grid
  (testing "Invalid cases"
    (is (false? (is-valid? [[1 1] [1 2]] 2)))  ; Duplicate in columns
    (is (false? (is-valid? [[1 2 3] [3 1 2] [3 2 1]] 3))))) ; 3rd row problem

(deftest test-rotate-row
  (testing "Rotates a single row in a matrix"
    (is (= (rotate-row [[1 2 3] [4 5 6]] 0) [[2 3 1] [4 5 6]]))
    (is (= (rotate-row [[1 2] [3 4]] 1) [[1 2] [4 3]]))
    (is (= (rotate-row [[5 6 7 8] [1 2 3 4]] 0) [[6 7 8 5] [1 2 3 4]]))
    (is (= (rotate-row [[0 1 2] [3 4 5]] 1) [[0 1 2] [4 5 3]]))
    (is (= (rotate-row [[9 8 7] [6 5 4] [3 2 1]] 2) [[9 8 7] [6 5 4] [2 1 3]]))))

(deftest test-solve
  (testing "Solves a small valid matrix"
    (is (= (solve [[1 2] [2 1]]) [[1 2] [2 1]])))
  (testing "Solves a 3x3 matrix"
    (is (= (solve [[1 2 3] [3 1 2] [2 3 1]]) [[1 2 3] [3 1 2] [2 3 1]])))
  (testing "Returns nil for an unsolvable matrix"
    (is (nil? (solve [[1 2 3] [2 1 3] [3 2 1]]))))
  (testing "Returns nil for an unsolvable matrix"
    (is (nil? (solve [[1 1] [1 2]]))))
  (testing "Solves a 4x4 matrix"
    (is (= (solve [[1 2 3 4] [3 4 1 2] [3 4 1 2] [4 1 2 3]])
           [[1 2 3 4] [3 4 1 2] [4 1 2 3] [2 3 4 1]])))
  (testing "Solves a 5x5 matrix"
    (is (= (solve [[1 2 3 4 5]
                   [2 3 4 5 1]
                   [4 5 1 2 3]
                   [4 5 1 2 3]
                   [5 1 2 3 4]])
           [[1 2 3 4 5]
            [2 3 4 5 1]
            [4 5 1 2 3]
            [5 1 2 3 4]
            [3 4 5 1 2]])))
  (testing "Returns nil for a 3x3 unsolvable case"
    (is (nil? (solve [[1 1 2]
                      [2 3 1]
                      [3 2 2]]))))
  (testing "Handles already solved case"
    (is (= (solve [[1 2 3 4]
                   [2 3 4 1]
                   [3 4 1 2]
                   [4 1 2 3]])
           [[1 2 3 4]
            [2 3 4 1]
            [3 4 1 2]
            [4 1 2 3]])))
  (testing "Handles a 1x1 trivial case"
    (is (= (solve [[1]]) [[1]])))
  (testing "Solves a case where initial rotations are needed"
    (is (= (solve [[1 2 3]
                   [3 1 2]
                   [1 2 3]])
           [[1 2 3]
            [3 1 2]
            [2 3 1]]))))

(run-tests)
