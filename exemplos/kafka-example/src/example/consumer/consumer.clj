(ns example.consumer
  (:gen-class)
  (:require
    [clojure.data.json :as json]
    [clojure.java.io :as jio]
    [example.logic :as logic]
    [example.properties :as properties])
  (:import
    (java.time Duration)
    (java.util Properties)
    (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)))


(defn consumer! [filename topic]
  (with-open [consumer (KafkaConsumer. (logic/build-properties filename #'properties/properties-consumer))]
    (.subscribe consumer [topic])
    (loop [tc 0
           records []]
      (let [new-tc (reduce
                     (fn [tc record]
                       (let [value (.value record)
                             cnt (get (json/read-str value) "count")
                             new-tc (+ tc cnt)]
                         (printf "Consumed record with key %s and value %s, and updated total count to %d\n"
                                 (.key record)
                                 value
                                 new-tc)
                         new-tc))
                     tc
                     records)]
        (println "Waiting for message in KafkaConsumer.poll")
        (recur new-tc (seq (.poll consumer (Duration/ofSeconds 1))))))))

(defn -main [& args]
  (apply consumer! args))
