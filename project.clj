(defproject pomodoro-tracker "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.7.0"]
                 [garden "1.3.2"]
                 [cljs-bach "0.3.0"]]

  :min-lein-version "2.5.3"

    :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-garden "0.3.0"]]

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "target"
                                    "test/js"
                                    "resources/public/css"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet   pomodoro-tracker.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

    :profiles
  {:dev
   {:dependencies [
                   [binaryage/devtools "0.9.9"]]

    :plugins      [[lein-figwheel "0.5.15"]
                   [lein-doo "0.1.8"]]}}
    

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "pomodoro-tracker.core/reload"}
     :compiler     {:main                 pomodoro-tracker.core
                    :optimizations        :none
                    :output-to            "resources/public/js/app.js"
                    :output-dir           "resources/public/js/dev"
                    :asset-path           "js/dev"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config
                    {:devtools/config
                     {:features-to-install    [:formatters :hints]
                      :fn-symbol              "F"
                      :print-config-overrides true}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            pomodoro-tracker.core
                    :optimizations   :advanced
                    :output-to       "resources/public/js/app.js"
                    :output-dir      "resources/public/js/min"
                    :elide-asserts   true
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:output-to     "resources/public/js/test.js"
                    :output-dir    "resources/public/js/test"
                    :main          pomodoro-tracker.runner
                    :optimizations :none}}]})
    
