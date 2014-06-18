(ns feture-flags.handler
  (:use compojure.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [clojure.java.io :as io]
            [clojure.data.json :as json]))

(def json-file (io/file 
	(io/resource "public/feature-flags.json")))

(def json-flags (json/read-json
	(slurp json-file) false))

 (defn get-all-flags []
 	(response (json/write-str json-flags)))

 (defn get-flag [flag]
 	(response (json/write-str (get json-flags flag))))

(defroutes app-routes
	(context "/flags"	[] (defroutes feature-flag-routes
		(GET "/" [] (get-all-flags)))
	(context "/:flag" [flag] (defroutes feature-flag-route
		(GET "/" [] (get-flag flag)))))
  (GET "/" [] "Hello World")	
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  ( -> (handler/api app-routes)
  	(middleware/wrap-json-body)
  	(middleware/wrap-json-response)))

