(ns advent-of-code-2021.day-1)

(require '[clojure.java.io]
         '[clojure.string])

(def depths
  (->> "day-1.txt"
       clojure.java.io/resource
       slurp
       clojure.string/split-lines
       (apply list)
       (map #(Integer/parseInt %))))

(defn- has-increased?
  "Compares two depths, returning true if `curr` is greater than `prev` and false otherwise."
  [prev curr]
  (cond
    (nil? prev) false
    :else (> curr prev)))

(defn- has-window-increased?
  "Compares 2 lists represent a window of depths, returning true if `curr` is greater than `prev` and false otherwise."
  [prev curr]
  (cond
    (empty? prev) false
    :else (has-increased? (apply + prev) (apply + curr))))

(defn- get-windows
  "Takes a list, `xs`, and a size, `n`, and returns a list of lists where each inner list contains `n` entries from `xs`."
  [xs n]
  (let [[window remaining] (split-at n xs)]
    (cond
      (empty? window) '()
      (empty? remaining) (list window)
      :else (concat (list window) (get-windows (rest xs) n)))))

(defn- get-increases
  "Takes a list of `depths` and a function, `f`, to compare 2 depths and returns a list of booleans indicating whether the depth at that position was an increase on the previous."
  [depths f]
  (cond
    (< (count depths) 2) '()
    :else (concat
           (->> depths
                (take 2)
                (apply f)
                list)
           (get-increases (rest depths) f))))

(defn- count-truthy
  "Counts the truthy values in a list."
  [increases]
  (->> increases
       (filter true?)
       count))

(defn count-increases
  "Counts the increases in depth in a list of depths."
  [depths]
  (-> depths
      (get-increases has-increased?)
      count-truthy))

(defn count-increases-by-window
  "Counts the increases in depth in a list of depths when comparing by a window of given size."
  [depths window-size]
  (-> depths
      (get-windows window-size)
      (get-increases has-window-increased?)
      count-truthy))

(defn run []
  (println (str "Part 1: " (count-increases depths)))
  (println (str "Part 2: " (count-increases-by-window depths 3))))
