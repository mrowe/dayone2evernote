;; https://github.com/bdesham/clj-plist/blob/master/src/com/github/bdesham/clj_plist.clj
;;
;; (inline because bdesham does not seem to have published it as a library)

(ns dayone2evernote.plist
  (:import (org.apache.commons.codec.binary Base64)
           (org.joda.time DateTime))
  (:require clojure.xml))

(defn- first-content
  [c]
  (first (c :content)))

(defmulti content (fn [c] (c :tag)))

(defmethod content :array
  [c]
  (apply vector (for [item (c :content)] (trampoline content item))))

(defmethod content :data
  [c]
  (.decode (Base64.) (first-content c)))

(defmethod content :date
  [c]
  (.toDate (DateTime. (first-content c))))

(defmethod content :dict
  [c]
  (apply hash-map (for [item (c :content)] (trampoline content item))))

(defmethod content :false
  [c]
  false)

(defmethod content :integer
  [c]
  (Long. (first-content c)))

(defmethod content :key
  [c]
  (first-content c))

(defmethod content :real
  [c]
  (Double. (first-content c)))

(defmethod content :string
  [c]
  (first-content c))

(defmethod content :true
  [c]
  true)

(defn parse-plist
  [source]
  (trampoline content (first-content (clojure.xml/parse source))))
