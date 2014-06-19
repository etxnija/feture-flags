(ns feture-flags.test.handler
  (:use clojure.test
        ring.mock.request  
        feture-flags.handler)
  (:require [clojure.data.json :as json]))

(deftest test-app

  (testing "flags route"
    (let [response (app (request :get "/flags"))]
      (is (= (:status response) 200))
      (is (= (count (json/read-json (:body response))) 2 ))))

  (testing "flag route"
    (let [response (app (request :get "/flags/A"))]
      (is (= (json/read-json (:body response)) "ON"))))

  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
