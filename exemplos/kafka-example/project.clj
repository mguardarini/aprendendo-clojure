(defproject example "0.1.0-SNAPSHOT"
  :description "This is a simple example to connect in Kafka using Cloud Confluent"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "0.2.6"]
                 [org.apache.kafka/kafka-clients "2.1.0"]]
  :aliases {"consumer" ["run" "-m" "example.consumer"]
            "producer" ["run" "-m" "example.producer"]})
