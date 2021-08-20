(ns app
  (:require [crux.api :as crux])
  (:import java.time.Duration))

(defn start-node []
  (crux/start-node {:crux/index-store
                    {:kv-store {:checkpointer
                                {:crux/module 'crux.checkpoint/->checkpointer
                                 :approx-frequency (Duration/ofSeconds 10)
                                 :store {:crux/module 'crux.checkpoint/->filesystem-checkpoint-store
                                         :path "./checkpointer"}}}}}))

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
    (println "Wait 11 seconds for generating checkpointer...")
    (Thread/sleep (* 11 1000))
    (println "Done")))