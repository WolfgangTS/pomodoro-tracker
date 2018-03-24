(ns pomodoro-tracker.timer
  (:require
    [reagent.core :as reagent]
    [cljs-bach.synthesis :as syn]))

(defonce context (syn/audio-context))

(defn ping [freq]
  (syn/connect->
    (syn/square freq)
    (syn/percussive 0.01 0.4)
    (syn/gain 0.4)))

(defn play-sound [sound & args]
  (-> (apply sound args)
      (syn/connect-> syn/destination)
      (syn/run-with context (syn/current-time context) 1.0)))

(defn expand-zeroes [n zeroes]
  (let [digit-count (count (str n))] 
    (if (< digit-count zeroes)
      (concat (take (- zeroes digit-count) (repeat \0)) (str n))
      (str n))))

(defn render-time [seconds]
  (let [minutes (js/Math.floor (/ seconds 60))
        seconds (mod seconds 60)]
    [:div.timer.title.is-1
        [:span.big-number minutes]
        [:span "m"]
        " "
        [:span.big-number (expand-zeroes seconds 2)]
        [:span "s"]]))

(def timer
  (reagent/atom 
    {:seconds 1500
     :state :stopped
     :timer :work
     :paused false}))

(defn play-pause []
  (swap! timer update-in [:paused] not))

(defn start []
  (swap! timer assoc-in [:state] :running))

(defn stop []
  (swap! timer assoc-in [:state] :stopped))

(defn reset-timer []
  (if (= :work (:timer @timer))
    (swap! timer assoc-in [:seconds] 1500)
    (swap! timer assoc-in [:seconds] 300)))

(defn next-task []
  (swap! timer update-in [:timer]
    (fn [current-timer]
        (cond (= current-timer :work) :pomodoro
              (= current-timer :pomodoro) :work))))
(defn skip []
  (start)
  (next-task)
  (reset-timer))

(defn clean [sym]
  [:u (subs (str sym) 1)])

(defn progress-bar [timer]
  (let [maximum (cond
                  (= :pomodoro (:timer timer)) 300
                  (= :work (:timer timer)) 1500)]
    [:progress.progress.is-info {:value (- maximum (:seconds timer))
                                 :max maximum}]))

(defn tick []
  (if (or (= :stopped (:state @timer)) (:paused @timer))
    (comment (println @timer))  
    (if (< (:seconds @timer) 2)
      (do 
        (play-sound ping 1000)
        (stop)
        (next-task)
        (reset-timer))
      (swap! timer update-in [:seconds] dec 1000))))
   
(defn render-component []
  [:div.container
    [:section.title
      [:h1.title.is-2
        (if (= :stopped (:state @timer))
          [:span "Ready to " (clean (:timer @timer))]
          [:span "Time to " (clean (:timer @timer)) "!"])]]
    [:section.time
      (render-time (:seconds @timer))
      (progress-bar @timer)]
    [:br]
    [:section.controls
      (if (or (= :stopped (:state @timer)))
        [:button.button.is-primary.is-large
          {:on-click start}
          [:span "Start"]
          [:span.icon.is-small [:i.fas.fa-play]]]
        (if (:paused @timer)
          [:button.button.is-primary.is-large
            {:on-click play-pause}
            [:span "Resume"]
            [:span.icon.is-small [:i.fas.fa-play]]]
          [:button.button.is-warning.is-large
            {:on-click play-pause}
            [:span "Pause"]
            [:span.icon.is-small [:i.fas.fa-pause]]]))
      (if (= :pomodoro (:timer @timer))
        [:span
          " "
          [:button.button.is-danger.is-large
            {:on-click skip}
            [:span "Skip"]
            [:span.icon.is-small [:i.fas.fa-times]]]])]])

