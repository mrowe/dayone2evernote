(ns dayone2evernote.evernote-xml-test
  (:require [clojure.data.xml :as xml])
  (:use clojure.test
        dayone2evernote.evernote-xml)
  (:import java.util.Date))

(deftest evernote-doc-test

  (let [now (Date.)
        entry {:title "A Title"
               :content "<p>Some content.</p>\n<p>More content.</p>"
               :created now}
        enex (xml/emit-str (evernote-doc [entry]))]

    ;; this is all pretty embarassing really
    (testing "Creates xml document"
      (is (re-find #"^<\?xml" enex)))

    (testing "Contains en-export tag"
      (is (re-find #"<en-export " enex)))

    (testing "Contains note tag"
      (is (re-find #"<note>" enex)))

    (testing "Contains the title"
      (is (re-find #"<title>A Title</title>" enex)))

    (testing "Contains the content as CDATA"
      (is (re-find #"<content><!\[CDATA\[" enex)))

    ;; (testing "Contains HTML tags in the content"
    ;;   (is (re-find #"<en-note><p>Some content.</p>" enex)))

    (testing "Contains the created date in the correct format"
      (is (re-find #"<created>\d{8}T\d{6}(\+\d{4}|Z)</created>" enex)))))
