(ns advent-of-code-2021.day-3
  (:require [clojure.java.io]
            [clojure.string]))

(def raw-diagnostics
  (->> "day-3.txt"
       clojure.java.io/resource
       slurp
       clojure.string/split-lines
       (apply list)
       ((fn [raw-diagnostics]
          (let [bits (map #(Integer/parseInt % 2) raw-diagnostics)
                width (count (first raw-diagnostics))]
            {:bits bits
             :width width})))))

(defn to-binary [bits]
  (reduce (fn [acc curr]
            (bit-or (bit-shift-left acc 1) curr)) bits))

(defn bit-at [n b]
  (let [mask (bit-shift-left 2r1 n)]
    (-> b
        (bit-and mask)
        (bit-shift-right n))))

(defn most-common-bit [bs n]
  (->> bs
       (map #(bit-at n %))
       (reduce +)
       (#(if
          (>= % (/ (count bs) 2))
           1
           0))))

(defn least-common-bit [bs n]
  (let [mcb (most-common-bit bs n)]
    (bit-xor mcb 2r1)))

(defn- rate-helper [f bs n]
  (cond
    (= 0 n) (list (f bs n))
    :else (concat (list (f bs n)) (rate-helper f bs (- n 1)))))

(defn gamma-rate [bs width]
  (rate-helper most-common-bit bs width))

(defn epsilon-rate [bs width]
  (rate-helper least-common-bit bs width))


(defn oxygen-generator-rating [bs n]
  (cond
    (= (count bs) 1) (first bs)
    :else (oxygen-generator-rating (filter #(= (bit-at n %) (most-common-bit bs n)) bs) (- n 1))))

(defn co2-scrubber-rating [bs n]
  (cond
    (= (count bs) 1) (first bs)
    :else (co2-scrubber-rating (filter #(= (bit-at n %) (least-common-bit bs n)) bs) (- n 1))))

(def bits (:bits raw-diagnostics))
(def width (:width raw-diagnostics))


(* (oxygen-generator-rating bits (- width  1))
   (co2-scrubber-rating bits (- width  1)))

(* (to-binary (gamma-rate bits (- width  1)))
   (to-binary (epsilon-rate bits (- width  1))))

