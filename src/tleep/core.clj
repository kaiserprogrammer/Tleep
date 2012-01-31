(ns tleep.core
  (:gen-class)
  (:import [java.io File FileInputStream BufferedInputStream])
  (:import [javazoom.jl.player Player]))

(defn wait-then-execute [seconds f]
  (do
    (Thread/sleep (* seconds 1000))
    (f)))

(defn play [filename]
  (loop []
    (let [player (Player. (BufferedInputStream.
                           (FileInputStream.
                            (File. (str (System/getProperty "user.home")
                                        "/"
                                        filename)))))]
      (.play player)
      (recur))))

(defn -main [& args]
  (let [seconds (Integer/parseInt (nth args 0))
        file (nth args 1)]
    (wait-then-execute seconds #(play file))))