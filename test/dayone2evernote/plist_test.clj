(ns dayone2evernote.plist-test
  (:use clojure.test
        dayone2evernote.plist))

(deftest parsing-test

  (let [plist (parse-plist (java.io.File. "test/sample.plist"))]

    (testing "Parses simple values"
      (is (= "A test.\n" (plist "Entry Text"))))

    (testing "Parses array values"
      (let [tags (plist "Tags")]
        (is (vector? tags))
        (is (= "Test" (first tags)))))

    (testing "Parses dict values"
      (let [location (plist "Location")]
        (is (map? location))
        (is (= "Australia" (location "Country")))))))
