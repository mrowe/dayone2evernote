(defproject dayone2evernote "0.1.0"
  :description "Read Day One entry files and write an Evernote export file."
  :url "https://github.com/mrowe/dayone2evernote"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [joda-time "1.6"]
                 [commons-codec/commons-codec "1.4"]
                 [org.clojure/tools.cli "0.2.2"]]
  :main dayone2evernote.core)
