(ns example.logic
    (:import
      (java.util Properties)))

(defn build-properties [filename properties]
       (with-open [config (jio/reader filename)]
                  (doto (Properties.)
                        (.putAll properties)
                        (.load config))))



