(ns pomodoro-tracker.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen []
  [:html :body {:background-color "hsl(0, 0%, 96%)"}]
  [:.big-number {:font-size "70px"}]
  [:.timer-app {}]
  [:.progress {:width "100%"
               :max-width "800px"}])
  
