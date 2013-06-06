[![Build Status](https://buildhive.cloudbees.com/job/mrowe/job/dayone2evernote/badge/icon)](https://buildhive.cloudbees.com/job/mrowe/job/dayone2evernote/)

# dayone2evernote

Read Day One entry files and write an Evernote export file.

## Installation

Download from https://github.com/mrowe/dayone2evernote and run `lein jar`.

## Usage

`dayone2evernote` takes two arguments: a path to the directory
containing Day One entry files (e.g. ~/Dropbox/Apps/Day
One/Journal.dayone/entries) and the name of the file in which to store
the Evernote export-format file:

    $ java -jar dayone2evernote-0.1.0-standalone.jar ~/Dropbox/Apps/Day\ One/Journal.dayone/entries/ dayone-notes.enex


## Bugs

 * Note title is derived by pulling the first sentence from the first
   line of the Day One note content, truncating to 80 chars if
   necessary. This is pretty naive.

 * Tags don't seem to get imported by Evernote. I don't know why not.

## History

### 0.2.1

 * Fix escaping bugs some more.

 * Put content that can't be parsed as XML into a <pre> block.

 * Handle empty content better.

### 0.2.0

 * Fix escaping and date bugs.
 
 * Import *all* entries.

### 0.1.0

 * Initial release.

## License

Copyright © 2013 Michael Rowe

Includes code Copyright © 2011–2012 Benjamin D. Esham ([www.bdesham.info](www.bdesham.info)).

Distributed under the Eclipse Public License, the same as Clojure.
