(ns state-talk.slides
  (:use clojure.repl))
;; C-c M-n  switch to current namespace










;; file example
(def file (atom "file contents"))
file
@file
(def hard-link file)
(= file hard-link)
(def before @file)
(swap! file (fn [x] (str x " after update")))
(def after @file)









;; river example
(def great-river (atom [1 2 3 4 5]))
(def mississippi great-river)
(future
  (dotimes [n 120]
    (Thread/sleep 1000)
    (swap! great-river (fn [coll] (map inc coll)))))










;; simultaneous web requests

(defn request [url]
  (Thread/sleep 10000)
  (str "content from " url))

(def a1 (agent nil))
(def a2 (agent nil))
(def a3 (agent nil))

(send-off a1 (fn [_] (request "first")))
(send-off a2 (fn [_] (request "second")))
(send-off a3 (fn [_] (request "third")))

(map (fn [a] (await a) @a) [a1 a2 a3])
