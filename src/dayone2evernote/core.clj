(ns dayone2evernote.core
  (:require [clojure.data.xml :as xml]
            [clojure.string :as string]
            [markdown.core :as md])
  (:gen-class))

(use 'dayone2evernote.plist)
(use 'dayone2evernote.evernote-xml)

(defn input-files
  [dir]
  (filter #(.endsWith (.getName %) ".doentry") (file-seq (clojure.java.io/file dir))))
  
(defn entry
  [file]
  (parse-plist file))

(defn dayone-entries
  [dir]
  (let [files (take 10 (input-files dir))]
    (map entry files)))

(defn- title
  "Derives a title from the content text."
  [text]
  (first (.split #"\." text)))

(defn- html-content
  "Parse content as markdown and return html"
  [content]
  (md/md-to-html-string content))

(defn dayone-entry-to-evernote
  "Return a data map for entry"
  [entry]
  {:title   (title (entry "Entry Text"))
   :content (html-content (entry "Entry Text"))
   :created (entry "Creation Date")
   :updated (entry "Creation Date")
   :tags    (entry "Tags")})

(defn -main
  ""
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  (if (< (count args) 2) (binding [*out* *err*]
                           (println "Usage: $0 <input-dir> <output-file>") 
                           (System/exit 1)))
 
  (let [[in out] args]
    (with-open [out-file (java.io.FileWriter. out)]
      (xml/emit (evernote-doc (map dayone-entry-to-evernote (dayone-entries in))) out-file))
    (str "Wrote Evernote export file to " out)))
