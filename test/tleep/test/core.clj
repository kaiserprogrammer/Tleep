(ns tleep.test.core
  (:use [tleep.core])
  (:use [clojure.test]))

(def executed (atom nil))

(defn setup-and-teardown [f]
  (reset! executed nil)
  (f)
  (reset! executed nil))

(use-fixtures :each setup-and-teardown)

(deftest waiting-and-executing
  (is (nil? @executed))
  (wait-then-execute 1 (fn [] (reset! executed true)))
  (is @executed))

(deftest expected-time-waited
  (is (nil? @executed))
  (let [start (System/currentTimeMillis)]
    (wait-then-execute 1 (fn [] (reset! executed true)))
    (let [stop (System/currentTimeMillis)]
      (is (not (< (- stop start) 1000)))
      (is (not (> (- stop start) 1200)))
      (is @executed))))

(deftest expected-time-waited2
  (is (nil? @executed))
  (let [start (System/currentTimeMillis)]
    (wait-then-execute 2 (fn [] (reset! executed true)))
    (let [stop (System/currentTimeMillis)]
      (is (not (< (- stop start) 2000)))
      (is (not (> (- stop start) 2200)))
      (is @executed))))