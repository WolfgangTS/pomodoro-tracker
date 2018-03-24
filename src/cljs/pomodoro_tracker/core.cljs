(ns pomodoro-tracker.core
  (:require
   [reagent.core :as reagent]
   [pomodoro-tracker.timer :as timer]))
   


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(def app-state
  (reagent/atom {}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn get-color [color]
  color {:pomodoro :.is-info
         :break    :.is-primary})
    
(defn page [ratom]
  [:div
    [:section.hero.is-small.is-info.is-bold
      [:div.hero-body
        [:div.container
          [:div.columns
            [:div.column.is-8-desktop.is-offset-2-desktop
              [:h1.title.is-2.is-spaced "Pomodoro Tracker"]
              [:h2.subtitle.is-4 "Be sharp, stay sharp"]]]]]]

    [:section.hero.is-medium.is-white 
      [:div.hero-body
        [:div.container.timer-app
          [:div.columns
            [:div.column.is-8-desktop.is-offset-2-desktop
              (timer/render-component)]]]]]
               
    [:section.section
      [:div.content
        [:div.columns
          [:div.column.is-8-desktop.is-offset-2-desktop
            [:div.content
              [:h2.title.is-3 "What is it good for?"]
              [:h3 "Keep focused while you are working on projects
                    by taking short breaks in 25 minute intervals
                    and don't overstress yourself."]]]]]]

    [:div.is-divider] 

    [:footer.footer.has-text-centered
      [:div.container
        [:div.columns
          [:div.column.is-8-desktop.is-offset-2-desktop
            [:p 
              "Made with love, and with " 
              [:strong.has-text-weight-semibold "Bulma"] 
              " - " 
              "Written in "
              [:strong.has-text-weight-semibold "ClojureScript"]]]]]]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))
    

(defn reload []
  (reagent/render [page app-state]
                  (.getElementById js/document "app"))
  (js/setInterval timer/tick 1000))

(defn ^:export main []
  (dev-setup)
  (reload))
