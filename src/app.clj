(ns app
  (:require [crux.api :as crux]))

(defn start-node []
  (crux/start-node {}))

(defn -main []
  (let [node (start-node)
        tx-1 [:crux.tx/put
              {:crux.db/id :dbpedia.resource/Pablo-Picasso :first-name :Pablo}
              #inst "2018-05-18T09:20:27.966-00:00"
              #inst "2018-05-19T08:31:15.966-00:00"]
        tx-2 [:crux.tx/put
              {:crux.db/id :dbpedia.resource/ivan :first-name :Ivan}
              #inst "2018-05-18T09:20:27.966-00:00"
              #inst "2018-05-19T08:31:15.966-00:00"]]
    (println "Submit tx")
    (crux/submit-tx node [tx-1 tx-2])
    (println "Sleep ...")
    (Thread/sleep (* 1 1000))
    (println "Done")))