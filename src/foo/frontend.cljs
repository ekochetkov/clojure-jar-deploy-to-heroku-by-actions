(ns foo.frontend
  (:require [re-frame.core :as rf]
            [reagent.dom :as rdom]))

(rf/reg-sub :app-state
  (fn [app-state] app-state))

(rf/reg-event-db :tik
  (fn [app-state [_ datetime]]
    (assoc app-state :datetime datetime)))

(defn fill-zeros [len src]
  (str (reduce str (repeat
     (- len (count (str src))) \0))
       src))

(defn format-date [datetime]
  (let [{Y :Y M :M D :D} datetime]
    (str Y "-"
         (fill-zeros 2 M) "-"
         (fill-zeros 2 D))))

(defn format-time [datetime]
  (let [{H :H i :i s :s t :t} datetime]
    (str (fill-zeros 2 H) ":"
         (fill-zeros 2 i) ":"
         (fill-zeros 2 s) "."
         (fill-zeros 3 t))))

(defn ui []
  (let [{dt :datetime} @(rf/subscribe [:app-state])
        H (:H dt)
        mode (if (or (>= H 22) (< H 6))
               {:color "white" :background "black"}
               {:color "black" :background "white"})]
    [:div {:style (merge mode {:font-family "Consolas"
                   :text-align "center"
                   :display "flex"
                   :justify-content "center"
                   :flex-direction "column"
                   :height "100%"
                   :width "100%"})}
     [:p {:style {:margin "0px" :font-size "118pt"}}
      (format-time dt)]
     [:p {:style {:margin "0px" :font-size "44pt"}}
      (format-date dt)]]))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el) 
    (rdom/render [ui] root-el))) 


(defn get-current-datetime []
  (let [date (new js/Date)]
        {:Y (.getFullYear date)
         :M (fill-zeros 2 (.getMonth date))
         :D (fill-zeros 2 (.getDate date))
         :H (fill-zeros 2 (.getHours date))
         :i (fill-zeros 2 (.getMinutes date))
         :s (fill-zeros 2 (.getSeconds date))
         :t (fill-zeros 3 (.getMilliseconds date))}))

(defn main []
  (js/setInterval
      #(rf/dispatch [:tik (get-current-datetime)]))
  (mount-root))

(main)
