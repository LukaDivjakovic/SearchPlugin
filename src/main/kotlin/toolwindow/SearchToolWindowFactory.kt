package toolwindow

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import lukaluka.searchplugin.forms.FileSearch
import searchLogic.searchForTextOccurrences
import java.nio.file.Paths
import javax.swing.SwingUtilities

class SearchToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val form = FileSearch()
        val panel = form.contentPane
        val content = ContentFactory.getInstance().createContent(panel, null, false)
        toolWindow.contentManager.addContent(content)

        // Coroutine scope tied to the content lifecycle
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        content.setDisposer { scope.cancel() }

        var currentJob: Job? = null

        form.startSearchButton.addActionListener {
            // cancel any existing search
            currentJob?.cancel()
            form.clearResults()

            val dirText = form.directoryPath
            val query = form.stringToSearch

            val path = try {
                Paths.get(dirText)
            } catch (_: Exception) {
                null
            }

            if (path == null || query.isEmpty()) {
                SwingUtilities.invokeLater {
                    form.appendResultLine("Please enter a valid directory and a non-empty search string.")
                }
                return@addActionListener
            }

            currentJob = scope.launch {
                try {
                    searchForTextOccurrences(query, path).collect { occ ->
                        val line = occ.toString()
                        SwingUtilities.invokeLater { form.appendResultLine(line) }
                    }
                } catch (_: Exception) {
                    SwingUtilities.invokeLater { form.appendResultLine("Search failed.") }
                }
            }
        }

        form.cancelSearchButton.addActionListener {
            currentJob?.cancel()
            currentJob = null
            form.appendResultLine("Search cancelled.")
        }
    }
}
