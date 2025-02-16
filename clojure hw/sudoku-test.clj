(ns hw1-test
  (:require
   [clojure.test :refer [deftest is run-tests testing]]
   [sudoku :refer :all]))

;;;; 1 Sudoku Solver

(deftest solve-sudoku-test
  (testing "Solve 1st sudoku"
    (let [grid [[5 3 0 0 7 0 0 0 0]
                [6 0 0 1 9 5 0 0 0]
                [0 9 8 0 0 0 0 6 0]
                [8 0 0 0 6 0 0 0 3]
                [4 0 0 8 0 3 0 0 1]
                [7 0 0 0 2 0 0 0 6]
                [0 6 0 0 0 0 2 8 0]
                [0 0 0 4 1 9 0 0 5]
                [0 0 0 0 8 0 0 7 9]]
          solution [[5 3 4 6 7 8 9 1 2]
                    [6 7 2 1 9 5 3 4 8]
                    [1 9 8 3 4 2 5 6 7]
                    [8 5 9 7 6 1 4 2 3]
                    [4 2 6 8 5 3 7 9 1]
                    [7 1 3 9 2 4 8 5 6]
                    [9 6 1 5 3 7 2 8 4]
                    [2 8 7 4 1 9 6 3 5]
                    [3 4 5 2 8 6 1 7 9]]]
      (is (= (solve grid) solution))))

  (testing "Solve 2nd sudoku"
    (let [grid [[8 0 0 0 0 0 0 0 0]
                [0 0 3 6 0 0 0 0 0]
                [0 7 0 0 9 0 2 0 0]
                [0 5 0 0 0 7 0 0 0]
                [0 0 0 0 4 5 7 0 0]
                [0 0 0 1 0 0 0 3 0]
                [0 0 1 0 0 0 0 6 8]
                [0 0 8 5 0 0 0 1 0]
                [0 9 0 0 0 0 4 0 0]]
          solution [[8 1 2 7 5 3 6 4 9]
                    [9 4 3 6 8 2 1 7 5]
                    [6 7 5 4 9 1 2 8 3]
                    [1 5 4 2 3 7 8 9 6]
                    [3 6 9 8 4 5 7 2 1]
                    [2 8 7 1 6 9 5 3 4]
                    [5 2 1 9 7 4 3 6 8]
                    [4 3 8 5 2 6 9 1 7]
                    [7 9 6 3 1 8 4 5 2]]]
      (is (= (solve grid) solution))))

  (testing "Solve 3rd sudoku"
    (let [grid [[9 0 0 0 0 0 0 0 2]
                [0 1 0 0 5 0 9 4 0]
                [0 0 3 0 0 0 0 0 7]
                [0 0 0 0 8 0 0 1 0]
                [6 0 0 0 0 0 0 0 3]
                [0 7 0 0 9 0 0 0 0]
                [2 0 0 0 6 0 7 0 0]
                [0 5 4 0 0 0 0 0 0]
                [7 0 0 0 2 0 0 0 0]]
          solution [[9 6 5 4 7 8 1 3 2]
                    [8 1 7 2 5 3 9 4 6]
                    [4 2 3 9 1 6 5 8 7]
                    [5 4 2 3 8 7 6 1 9]
                    [6 9 1 5 4 2 8 7 3]
                    [3 7 8 6 9 1 4 2 5]
                    [2 3 9 8 6 4 7 5 1]
                    [1 5 4 7 3 9 2 6 8]
                    [7 8 6 1 2 5 3 9 4]]]
      (is (= (solve grid) solution))))

  (testing "Solve empty sudoku by brute force"
    (let [empty-grid [[0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]
                      [0 0 0 0 0 0 0 0 0]]
          solution [[1 2 3 4 5 6 7 8 9]
                    [4 5 6 7 8 9 1 2 3]
                    [7 8 9 1 2 3 4 5 6]
                    [2 1 4 3 6 5 8 9 7]
                    [3 6 5 8 9 7 2 1 4]
                    [8 9 7 2 1 4 3 6 5]
                    [5 3 1 6 4 2 9 7 8]
                    [6 4 2 9 7 8 5 3 1]
                    [9 7 8 5 3 1 6 4 2]]]
      (is (= (solve empty-grid) solution))))

  (testing "Sudoku with no solution"
    (let [unsolvable-grid [[1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 0 9]
                           [1 2 3 0 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]
                           [1 2 3 4 5 6 7 8 9]]]
      (is (nil? (solve unsolvable-grid)))))

  (testing "Solve 4th sudoku with unique solution"
    (let [unique-grid [[3 0 6 5 0 8 4 0 0]
                       [5 2 0 0 0 0 0 0 0]
                       [0 8 7 0 0 0 0 3 1]
                       [0 0 3 0 1 0 0 8 0]
                       [9 0 0 8 6 3 0 0 5]
                       [0 5 0 0 9 0 6 0 0]
                       [1 3 0 0 0 0 2 5 0]
                       [0 0 0 0 0 0 0 7 4]
                       [0 0 5 2 0 6 3 0 0]]
          solution [[3 1 6 5 7 8 4 9 2]
                    [5 2 9 1 3 4 7 6 8]
                    [4 8 7 6 2 9 5 3 1]
                    [2 6 3 4 1 5 9 8 7]
                    [9 7 4 8 6 3 1 2 5]
                    [8 5 1 7 9 2 6 4 3]
                    [1 3 8 9 4 7 2 5 6]
                    [6 9 2 3 5 1 8 7 4]
                    [7 4 5 2 8 6 3 1 9]]]
      (is (= (solve unique-grid) solution)))))

;;;; 2 Transform tests

(deftest transform-test
  (testing "Transform grid with no 0s"
    (let [grid [[1 2 3 4 5 6 7 8 9]
                [4 5 6 7 8 9 1 2 3]
                [7 8 9 1 2 3 4 5 6]
                [2 3 4 5 6 7 8 9 1]
                [5 6 7 8 9 1 2 3 4]
                [8 9 1 2 3 4 5 6 7]
                [3 4 5 6 7 8 9 1 2]
                [6 7 8 9 1 2 3 4 5]
                [9 1 2 3 4 5 6 7 8]]
          expected-grid [[#{1} #{2} #{3} #{4} #{5} #{6} #{7} #{8} #{9}]
                         [#{4} #{5} #{6} #{7} #{8} #{9} #{1} #{2} #{3}]
                         [#{7} #{8} #{9} #{1} #{2} #{3} #{4} #{5} #{6}]
                         [#{2} #{3} #{4} #{5} #{6} #{7} #{8} #{9} #{1}]
                         [#{5} #{6} #{7} #{8} #{9} #{1} #{2} #{3} #{4}]
                         [#{8} #{9} #{1} #{2} #{3} #{4} #{5} #{6} #{7}]
                         [#{3} #{4} #{5} #{6} #{7} #{8} #{9} #{1} #{2}]
                         [#{6} #{7} #{8} #{9} #{1} #{2} #{3} #{4} #{5}]
                         [#{9} #{1} #{2} #{3} #{4} #{5} #{6} #{7} #{8}]]]
      (is (= (transform grid) expected-grid))))

  (testing "Transform grid with one 0"
    (let [grid-with-zero [[1 2 3 4 5 6 7 8 9]
                          [4 5 6 7 8 9 1 2 3]
                          [7 8 9 1 2 3 4 5 6]
                          [2 3 4 5 6 7 8 9 1]
                          [5 6 7 8 9 1 2 3 4]
                          [8 9 1 2 3 4 5 6 7]
                          [3 4 5 6 7 8 9 1 2]
                          [6 7 8 9 1 2 3 4 5]
                          [9 1 2 3 4 5 6 7 0]]
          expected-grid [[#{1} #{2} #{3} #{4} #{5} #{6} #{7} #{8} #{9}]
                         [#{4} #{5} #{6} #{7} #{8} #{9} #{1} #{2} #{3}]
                         [#{7} #{8} #{9} #{1} #{2} #{3} #{4} #{5} #{6}]
                         [#{2} #{3} #{4} #{5} #{6} #{7} #{8} #{9} #{1}]
                         [#{5} #{6} #{7} #{8} #{9} #{1} #{2} #{3} #{4}]
                         [#{8} #{9} #{1} #{2} #{3} #{4} #{5} #{6} #{7}]
                         [#{3} #{4} #{5} #{6} #{7} #{8} #{9} #{1} #{2}]
                         [#{6} #{7} #{8} #{9} #{1} #{2} #{3} #{4} #{5}]
                         [#{9} #{1} #{2} #{3} #{4} #{5} #{6} #{7} #{1 2 3 4 5 6 7 8 9}]]]
      (is (= (transform grid-with-zero) expected-grid))))

  (testing "Transform large grid"
    (is (= (transform [[5 3 4 0 7 3 0 0 1]
                     [6 1 0 1 9 5 2 0 0]
                     [0 9 8 3 0 1 0 6 7]
                     [8 0 9 1 6 5 0 0 3]
                     [4 2 0 8 0 3 3 0 1]
                     [7 0 1 1 2 1 0 0 6]
                     [0 6 2 0 1 3 2 8 0]
                     [1 1 0 4 1 9 0 0 5]
                     [1 1 1 0 8 0 0 7 9]])
         [[#{5} #{3} #{4} #{1 2 3 4 5 6 7 8 9} #{7} #{3} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9} #{1}]
          [#{6} #{1} #{1 2 3 4 5 6 7 8 9} #{1} #{9} #{5} #{2} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9}]
          [#{1 2 3 4 5 6 7 8 9} #{9} #{8} #{3} #{1 2 3 4 5 6 7 8 9} #{1} #{1 2 3 4 5 6 7 8 9} #{6} #{7}]
          [#{8} #{1 2 3 4 5 6 7 8 9} #{9} #{1} #{6} #{5} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9} #{3}]
          [#{4} #{2} #{1 2 3 4 5 6 7 8 9} #{8} #{1 2 3 4 5 6 7 8 9} #{3} #{3} #{1 2 3 4 5 6 7 8 9} #{1}]
          [#{7} #{1 2 3 4 5 6 7 8 9} #{1} #{1} #{2} #{1} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9} #{6}]
          [#{1 2 3 4 5 6 7 8 9} #{6} #{2} #{1 2 3 4 5 6 7 8 9} #{1} #{3} #{2} #{8} #{1 2 3 4 5 6 7 8 9}]
          [#{1} #{1} #{1 2 3 4 5 6 7 8 9} #{4} #{1} #{9} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9} #{5}]
          [#{1} #{1} #{1} #{1 2 3 4 5 6 7 8 9} #{8} #{1 2 3 4 5 6 7 8 9} #{1 2 3 4 5 6 7 8 9} #{7} #{9}]])))

  (testing "Transform smaller grids"
    (is (= (transform [[1 2 3]]) [[#{1} #{2} #{3}]]))
    (is (= (transform [[0] [0]])) [[#{1 2 3 4 5 6 7 8 9}] [#{1 2 3 4 5 6 7 8 9}]])
    (is (= (transform [[]]) [[]]))
    (is (= (transform [[1 2 0 3]]) [[#{1} #{2} #{1 2 3 4 5 6 7 8 9} #{3}]]))))

;;;; Util tests

(def grid [[1 2 3 4 5 6 7 8 9]
           [4 5 6 7 8 9 1 2 3]
           [7 8 9 1 2 3 4 5 6]
           [2 3 4 5 6 7 8 9 1]
           [5 6 7 8 9 1 2 3 4]
           [8 9 1 2 3 4 5 6 7]
           [3 4 5 6 7 8 9 1 2]
           [6 7 8 9 1 2 3 4 5]
           [9 1 2 3 4 5 6 7 8]])

(deftest row-contains-test
  (is (true? (row-contains? [1 2 3 4 5] 4)))
  (is (nil? (row-contains? [1 2 3 4 5] 6)))
  (is (nil? (row-contains? [] 1))))

(deftest col-contains-test
  (is (true? (col-contains? [[1 2 3]
                             [4 5 6]
                             [7 8 9]] 5 1)))
  (is (nil? (col-contains? [[1 2 3]
                            [4 5 6]
                            [7 8 9]] 5 2)))
  (is (nil? (col-contains? [] 1 1))))

(deftest get-rows-test
  (testing "should get the first 3 rows for quadrant 0"
    (is (= (get-rows grid 0) [[1 2 3 4 5 6 7 8 9]
                              [4 5 6 7 8 9 1 2 3]
                              [7 8 9 1 2 3 4 5 6]])))

  (testing "should get the middle 3 rows for quadrant 4"
    (is (= (get-rows grid 4) (subvec grid 3 6))))

  (testing "should get the last 3 rows for quadrant 8"
    (is (= (get-rows grid 8) (subvec grid 6 9)))))

(deftest sub-contains?-sudoku-test
  (testing "should find a match in the first 3x3 quadrant"
    (is (true? (quadrant-contains? grid 1 0)))
    (is (true? (quadrant-contains? grid 5 1)))
    (is (nil? (quadrant-contains? grid 10 2))))

  (testing "should find a match in the second 3x3 quadrant"
    (is (true? (quadrant-contains? grid 7 3)))
    (is (true? (quadrant-contains? grid 2 4)))
    (is (nil? (quadrant-contains? grid 10 5))))

  (testing "should find a match in the third 3x3 quadrant"
    (is (true? (quadrant-contains? grid 4 6)))
    (is (true? (quadrant-contains? grid 9 7)))
    (is (nil? (quadrant-contains? grid 10 8)))))

(def grid-with-one-empty [[1 2 3 4 5 6 7 8 9]
                          [4 5 6 7 8 9 1 2 3]
                          [7 8 9 1 2 3 4 5 6]
                          [2 3 4 5 6 7 8 9 1]
                          [5 6 7 8 0 1 2 3 4]
                          [8 9 1 2 3 4 5 6 7]
                          [3 4 5 6 7 8 9 1 2]
                          [6 7 8 9 1 2 3 4 5]
                          [9 1 2 3 4 5 6 7 8]])

(deftest find-empty-cell-test
  (testing "should find the first empty cell in an empty grid"
    (is (= (find-empty-cell [[0]]) [0 0])))

  (testing "should find the first empty cell in a grid with one empty cell"
    (is (= (find-empty-cell grid-with-one-empty) [4 4]))
    (is (= (find-empty-cell [[1 2 3 0 4]]) [0 3]))
    (is (= (find-empty-cell [[1 2 3 4 5 6 7 8 9] [4 5 0]]) [1 2])))

  (testing "should return nil for a completely filled grid"
    (is (nil? (find-empty-cell (vec (repeat 9 (vec (repeat 9 1)))))))))

(run-tests)
