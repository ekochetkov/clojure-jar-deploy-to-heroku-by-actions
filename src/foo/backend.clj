(ns foo.backend
  (:gen-class)
  (:require
   [org.httpkit.server :refer [run-server]]
   [compojure.core :refer [defroutes GET]]
   [compojure.route :refer [resources]]
   [hiccup.core :as hp]))

(defonce server-stop-func (atom nil))

(defn index-page [_]
  (hp/html
    [:html
      [:head
        [:title "Foo project page"]
      [:body
        [:div#app]
        [:script {:src "js/main.js"}]]]]))

(defroutes app
  (GET "/" [] index-page)
  (resources "/"))

(defn run-server-at-port [port]
  (run-server #'app {:port port}))

(defn -main []
  (let [port (-> "PORT" System/getenv Integer/parseInt)]
  (reset! server-stop-func (run-server #'app {:port port}))))

;(@server-stop-func)
;(-main)
