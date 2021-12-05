(ns advent-of-code-2021.day-2
  (:require [clojure.java.io]
            [clojure.string]))

(def course
  (->> "day-2.txt"
       clojure.java.io/resource
       slurp
       clojure.string/split-lines
       (apply list)
       (map #(let [command (clojure.string/split % #" ")]
               (list (first command) (Integer/parseInt (first (rest command))))))))

(def origin '(0 0))

(def origin-with-aim '(0 0 0))

(defn position [horizontal depth]
  (list horizontal depth))

(defn position-with-aim [horizontal depth aim]
  (list horizontal depth aim))

(defn horizontal [pos]
  (first pos))

(defn depth [pos]
  (first (rest pos)))

(defn aim [pos]
  (last pos))

(defn move [from command]
  (let [direction (first command)
        distance (first (rest command))
        h (horizontal from)
        d (depth from)]
    (case direction
      "forward" (position (+ h distance) d)
      "down" (position h (+ d distance))
      "up" (position h (- d distance)))))

(defn move-with-aim [from command]
  (let [direction (first command)
        distance (first (rest command))
        h (horizontal from)
        d (depth from)
        a (aim from)]
    (case direction
      "forward" (position-with-aim (+ h distance) (+ d (* a distance)) a)
      "down" (position-with-aim h d (+ a distance))
      "up" (position-with-aim h d (- a distance)))))

(defn follow-course [move start course]
  (cond
    (empty? course) start
    :else (follow-course move (move start (first course)) (rest course))))

(defn calculate-final-value [move origin course]
  (let [final-position (follow-course move origin course)]
    (* (horizontal final-position) (depth final-position))))

(defn run []
  (println (str "Part 1: " (calculate-final-value move origin course)))
  (println (str "Part 2: " (calculate-final-value move-with-aim origin-with-aim course))))
