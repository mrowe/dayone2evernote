# dayone2evernote

Read Day One entry files and write an Evernote export file.

## Installation

Download from https://github.com/mrowe/dayone2evernote.

## Usage

`dayone2evernote` takes two arguments: a path to the directory
containing Day One entry files (e.g. ~/Dropbox/Apps/Day
One/Journal.dayone/entries) and the name of the file in which to store
the Evernote export-format file:

    $ java -jar dayone2evernote-0.1.0-standalone.jar ~/Dropbox/Apps/Day\ One/Journal.dayone/entries/ dayone-notes.enex


## Bugs

 * Evernote does not seem to read "created" dates

 * Embedded content doc is incorrectly escaping element tags

 * Note title is derived by pulling the first sentence from the Day
   One note content. This is pretty naive, and at the very least
   should limit the length of the title.

 * NOTE: currently only reads the first 10 Day One entries, for testing

## History

### 0.1.0

 * Initial release.

## License

Copyright © 2013 Michael Rowe

Distributed under the Eclipse Public License, the same as Clojure.
