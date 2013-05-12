(ns dayone2evernote.core
  (:require [clojure.data.xml :as xml]
            [clojure.tools.cli :only [cli]])
  (:gen-class))

(use 'dayone2evernote.plist)
(use 'dayone2evernote.evernote-xml)

(def entry (parse-plist (java.io.File. "test/sample.plist")))

(defn dayone-entries [] [entry])

(defn first-line
  "Returns the first line of text."
  [text]
  (first (.split #"\n" text 2)))

(defn dayone-entry-to-evernote
  "Return a data map for entry"
  [entry]
  {:title   (first-line (entry "Entry Text"))
   :content (entry "Entry Text")
   :created (entry "Creation Date")
   :tags    (entry "Tags")})

(defn -main
  ""
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; (xml/emit (evernote-doc [(dayone-entry-to-evernote entry)]) *out*)

  (with-open [out-file (java.io.FileWriter. "/tmp/dayone-export.xml")]
    (xml/emit (evernote-doc (map dayone-entry-to-evernote (dayone-entries)))
          out-file))
)

