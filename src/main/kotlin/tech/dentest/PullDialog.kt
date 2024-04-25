package tech.dentest

import com.intellij.openapi.project.Project
import tech.dentest.http.Client
import tech.dentest.listeners.FeatureListSelectionListener
import tech.dentest.model.Feature
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBList
import javax.swing.JComponent

class PullDialog(private val project: Project?): DialogWrapper(true) {
    private val client = Client.getInstance()

    init {
        init()
        title = "Dentest Pull"
    }

    override fun createCenterPanel(): JComponent {
        val list = JBList(buildListModel())

        if (project != null) {
            list.addListSelectionListener(FeatureListSelectionListener(project))
        }

        return list
    }

    private fun buildListModel(): List<Feature> {
        if (project == null) {
            return emptyList()
        }

        return client.pullFeatures(project)
    }
}
