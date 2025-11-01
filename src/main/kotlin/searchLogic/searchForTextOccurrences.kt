package searchLogic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.useLines
import kotlin.streams.asSequence

interface Occurrence {
    val file: Path
    val line: Int
    val offset: Int
}

data class TextOccurrence(
    override val file: Path,
    override val line: Int,
    override val offset: Int
) : Occurrence

//@OptIn(ExperimentalCoroutinesApi::class)
fun searchForTextOccurrences(
    stringToSearch: String,
    directory: Path
): Flow<Occurrence> {
    if (stringToSearch.isEmpty()) {
        return emptyFlow()
    }

    if (!directory.exists() || !directory.isDirectory()) {
        return emptyFlow()
    }

    // Get all regular files in the directory tree
    val files = try {
        Files.walk(directory)
            .asSequence()
            .filter { it.isRegularFile() }
            .toList()
    } catch (e: Exception) {
        emptyList<Path>()
    }

    return channelFlow {
        for (file in files) {
            launch(Dispatchers.IO) {
                try {
                    val occurrences = searchInFile(file, stringToSearch)
                    for (occ in occurrences) send(occ)
                } catch (_: Exception) {
                    // ignore unreadable files
                }
            }
        }
    }


//    // Process files concurrently using flow operators
//    val concurrency = Runtime.getRuntime().availableProcessors()
//
//    return files.asFlow()
//        .map { file ->
//            flow {
//                try {
//                    searchInFile(file, stringToSearch).forEach { occurrence ->
//                        emit(occurrence)
//                    }
//                } catch (e: Exception) {
//                    // Skip files that can't be read (binary, permissions, etc.)
//                }
//            }
//        }
//        .flattenMerge(concurrency = concurrency)
}

private fun searchInFile(file: Path, searchString: String): List<Occurrence> {
    val occurrences = mutableListOf<Occurrence>()

    try {
        // useLines streams the file line by line
        file.useLines { lines ->
            lines.forEachIndexed { lineIndex, lineContent ->
                var startIndex = 0
                while (true) {
                    val index = lineContent.indexOf(searchString, startIndex)
                    if (index == -1) break

                    occurrences.add(
                        TextOccurrence(
                            file = file,
                            line = lineIndex + 1, // 1-indexed line numbers
                            offset = index
                        )
                    )
                    startIndex = index + 1 // Find overlapping occurrences
                }
            }
        }
    } catch (e: Exception) {
        // File is binary, unreadable, or other IO error - return the empty list
    }

    return occurrences
}