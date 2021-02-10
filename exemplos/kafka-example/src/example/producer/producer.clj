(ns example.producer
  (:gen-class)
  (:require
    [clojure.data.json :as json]
    [clojure.java.io :as jio]
    [example.logic :as logic]
    [example.properties :as properties])
  (:import
    (java.util Properties)
    (org.apache.kafka.clients.admin AdminClient NewTopic)
    (org.apache.kafka.clients.producer Callback KafkaProducer ProducerRecord)
    (org.apache.kafka.common.errors TopicExistsException)))


(defn- create-topic! [topic partitions replication cloud-config]
  (let [adminCloudConfig (AdminClient/create cloud-config)]
    (try
      (.createTopics adminCloudConfig [(NewTopic. topic partitions replication)])
      (catch TopicExistsException e nil)
      (finally
        (.close adminCloudConfig)))))

(defn create-message [key value]
      (printf "Producing record: %s\t%s\n" k v)
      (ProducerRecord. topic key value))


(defn producer! [filename topic]
  (let [props (logic/build-properties filename #'properties/properties-producer)
        print-ex (comp println (partial str "Failed to deliver message: "))
        print-metadata #(printf "Produced record to topic %s partition [%d] @ offest %d\n"
                                (.topic %)
                                (.partition %)
                                (.offset %))
        (create-message "mauro" (json/write-str {:count %}))

      (with-open [producer (KafkaProducer. props)]
      (create-topic! topic 1 3 props)
      (let [callback (reify Callback
                       (onCompletion [this metadata exception]
                         (if exception
                           (print-ex exception)
                           (print-metadata metadata))))]
        (doseq [i (range 5)]
          (.send producer (create-message "mauro" i) callback))
        (.flush producer)
        ;; Or we could wait for the returned futures to resolve, like this:
        (let [futures (doall (map #(.send producer (create-message "mauro" %)) (range 5 10)))]
          (.flush producer)
          (while (not-every? future-done? futures)
            (Thread/sleep 50))
          (doseq [fut futures]
            (try
              (let [metadata (deref fut)]
                (print-metadata metadata))
              (catch Exception e
                (print-ex e))))))
      (printf "10 messages were produced to topic %s!\n" topic))))

(defn -main [& args]
  (apply producer! args))
