(ns pomodoro-tracker.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [hiccup.page :refer [include-js]]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defroutes app
  (route/resources "/")
  (route/not-found #"404, not found"))

(def site (handler/site app))

(defonce ^:dynamic server nil)

(defn stop
  []
  (when server
    (.stop server)))

(defn start
  [& [port]]
  (stop)
  (alter-var-root
    #'server
    (constantly
      (jetty/run-jetty #'site
        {:prot (Long. (or port 5000))
         :join? false}))))

(defn -main
  [& [port]]
  (start port))
