# SearchPlugin — Simple File Text Search Tool Window

A tiny IntelliJ plugin that searches for a text string in all files under a directory and streams matching locations into a tool window. It’s meant to be easy to run and demo.

## Run
- Build: `./gradlew build`
- Start a sandbox IDE: `./gradlew runIde`
- In the IDE, open: View → Tool Windows → File Search

## Use
1. Enter an absolute directory path.
2. Enter the text to find.
3. Click Start. Matches appear as `path: line:offset`.
4. Click Cancel to stop the current search; the UI prints `Search cancelled.`

## How it works (brief)
- Start launches a background scan over regular files in the given directory and streams results to the UI as they are found.
- Errors print `Search failed.`
- Cancel stops the current job and prints `Search cancelled.`
- There is no special message when a search finishes.

## Notes
- Requires a recent JDK; built with Gradle (IntelliJ Gradle plugin).
- UI is Swing-based via IntelliJ’s GUI Designer.
