(ns dayone2evernote.evernote-xml
  (:require [clojure.data.xml :as xml])
  (:import java.text.SimpleDateFormat
           java.util.Date))

(def date-formatter (SimpleDateFormat. "yyyyMMdd'T'HHmmssZ"))
(defn- format-date [date] (.format date-formatter date))

(defn- now [] (format-date (Date.)))

(defn- tag-element
  [tag]
  (xml/element :tag {} tag))

(defn- content-document
  [text]
  (xml/element :en-note {} (xml/parse-str text)))
  
(defn- entry-element
  [entry]
  (xml/element :note {}
               (xml/element :title {} (:title entry))
               (xml/element :content {} (xml/cdata (xml/emit-str (content-document (:content entry)))))
               (xml/element :created {} (format-date (:created entry)))
               (map tag-element (:tags entry))))

(defn evernote-doc
  ""
  [entries]
  (let [entry (first entries)]
  (xml/element :en-export {:export-date (now)
                           :application "dayone2evernote"
                           :version     "1.0"}
               (map entry-element entries))))
