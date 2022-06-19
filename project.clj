(defproject foo "0.0.1-SNAPSHOT"
  :description "Clock application on fullstack Clojure/ClojureScript"
  :url "https://clojure-clock-fullstack-app.herokuapp.com/"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [http-kit "2.1.18"]
                 [compojure "1.3.4"]
                 [hiccup "1.0.5"]]
  :main foo.backend)
