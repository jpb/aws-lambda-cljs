(ns dns-resolver.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def dns (nodejs/require "dns"))
(def aws (nodejs/require "aws-sdk"))
(def uuid (nodejs/require "node-uuid"))

(defn- query->result [job err addresses]
  (let [result* (if (nil? err)
                   ;; then
                   {:status :success
                    :result addresses}
                   ;; else
                   {:status :failure
                    :result (.toString err)})]
    (merge job result*)))
  
(defn- persist-result [result]
;;  (let [db (aws.DynamoDB.)]
;;    (.putItem db {:TableName "dns_resolver"
;;                  :Item (js-obj result)}))
  result)

(defn- call-done-fn [result]
  (let [status (if (= (:status result) :success)
                 nil
                 "error")
        prepare-result (comp #(.stringify js/JSON %)
                             clj->js
                             #(dissoc % :done-fn))]
    ((:done-fn result) status (prepare-result result))))

(defn- handle-result [job err addrs]
  (->
    (query->result job err addrs)
    (persist-result)
    (call-done-fn)))

(defn resolve [job]
  (.resolve dns (:domain job) (:type job) #(handle-result job %1 %2)))

;; AWS Lambda entry point
(defn ^:export handler [event context]
  (resolve {:uuid (or (aget event "uuid") (.v4 uuid))
            :domain (aget event "domain")
            :type (aget event "type")
            :done-fn (aget context "done")}))

;; CLI entry point
(defn -main [domain type uuid]
  (resolve {:uuid (or uuid (.v4 uuid))
            :domain domain
            :type type
            :done-fn println}))

(set! *main-cli-fn* -main)
