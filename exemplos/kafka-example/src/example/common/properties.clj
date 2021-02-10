(ns example.properties
    (:import
      (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)
      (org.apache.kafka.clients.producer Callback KafkaProducer ProducerConfig ProducerRecord)))

(def properties-consumer {ConsumerConfig/GROUP_ID_CONFIG, "clojure_example_group"
                          ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringDeserializer"
                          ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringDeserializer"})


(def properties-producer {ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringSerializer"
                 ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringSerializer"})
