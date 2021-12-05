(ns advent-of-code-2021.core
  (:gen-class)
  (:require [advent-of-code-2021.day-1 :as day-1]
            [clojure.string :refer [upper-case]]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (seq args)
    (doseq [arg args]
      (println (upper-case arg))
      (case arg
        "day-1" (day-1/run)
        (println "Not found"))
      (println))
    (println "Nope")))
