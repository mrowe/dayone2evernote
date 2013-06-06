(ns dayone2evernote.evernote-xml
  (:require [clojure.data.xml :as xml])
  (:import java.text.SimpleDateFormat
           java.util.TimeZone
           java.util.Date))

(def date-formatter (SimpleDateFormat. "yyyyMMdd'T'HHmmss'Z'"))
(.setTimeZone date-formatter (TimeZone/getTimeZone "UTC"))

(defn- format-date [date] (.format date-formatter date))

(defn- now [] (format-date (Date.)))

;; lifted from hiccup
(defn- escape
  "Change special characters into XML character entities."
  [s]
  (if (nil? s)
    ""
    (.. ^String s
        (replace "&"  "&amp;")
        (replace "<"  "&lt;")
        (replace ">"  "&gt;")
        (replace "\"" "&quot;"))))

(defn- tag-element
  [tag]
  (xml/element :tag {} tag))

(defn- xml?
  [s]
  (try
    (xml/emit-str (xml/parse-str s))
    (catch Throwable e
      false)))

(defn- wrap
  "Wrap string s in xml-ish tag"
  [tag s]
  (str "<" tag ">" s "</" tag ">"))

(defn- content-string
  [entry]
  (wrap "en-note"
        (if (xml? (wrap "body" (:content entry)))
          (:content entry)
          (wrap "pre" (escape (:content-raw entry))))))

(defn- entry-element
  [entry]
  (xml/element :note {}
               (xml/element :title {} (:title entry))
               (xml/element :content {} (xml/cdata (content-string entry)))
               (xml/element :created {} (format-date (:created entry)))
               (xml/element :updated {} (format-date (:updated entry)))
               (map tag-element (:tags entry))))

(defn evernote-doc
  ""
  [entries]
  (xml/element :en-export {:export-date (now)
                           :application "dayone2evernote"
                           :version     "1.0"}
               (map entry-element entries)))
