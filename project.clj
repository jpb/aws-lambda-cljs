(defproject dns-resolver "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2657"]]

  :node-dependencies [[source-map-support "0.2.8"]
                      [aws-sdk "2.1.7"]
                      [node-uuid "1.4.2"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "dns-resolver"
              :source-paths ["src"]
              :notify-command ["node" "index.js" "example.com" "A"] 
              :compiler {
                :output-to "out/dns_resolver.js"
                :output-dir "out"
                :target :nodejs
                :optimizations :none
                :source-map true}}]})
