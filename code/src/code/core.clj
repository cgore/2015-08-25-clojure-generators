(ns code.core
  (:require [schema.core :as s]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

;; Give us random integers (ten by default.)
(gen/sample gen/int)

;; Give us 20 random integers.
(gen/sample gen/int 20)

;; A schema for integers.
(s/validate s/Int 42) ; passes, returns 42
(try (s/validate s/Int "nope") ; fails, throws an error.
     (catch Exception e)) ; we catch the error so this file will load cleanly

;; If you don't want an exception use check instead of validate.
(s/check s/Int 42) ; => nil
(s/check s/Int "nope") ; => (not (integer? "nope"))

;; Test Check Properties
(def prop-addition-increments
  (prop/for-all [a gen/int
                 b gen/int]
                (>= (+ a b) a))) ; This is always true.
;; Check 100 times
(tc/quick-check 100 prop-addition-increments)
;; FAIL!  We forgot negatives!
{:result false, :seed 1434746134125, :failing-size 2,
 :num-tests 3,:fail [1 -2],
 :shrunk {:total-nodes-visited 4, :depth 2, :result false,
          :smallest [0 -1]}}
