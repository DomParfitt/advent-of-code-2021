(ns advent-of-code-2021.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (seq args)
    (doseq [arg args]
      (println arg))
    (println "Nope")))
