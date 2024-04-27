package tech.dentest

import com.intellij.openapi.project.Project
import tech.dentest.http.Client
import tech.dentest.model.Feature
import com.intellij.openapi.ui.DialogWrapper
import tech.dentest.component.FeatureTree
import tech.dentest.settings.PluginSettings
import java.awt.Dimension
import javax.swing.JComponent

class PullDialog(private val project: Project): DialogWrapper(true) {
    private val client = Client.getInstance()

    init {
        init()
        title = "Dentest Pull"
    }

    override fun createCenterPanel(): JComponent {
        val features = buildListModel()
        val tree = FeatureTree(features, PluginSettings.getInstance(project).state)

        return tree.getTreeComponent()
    }

    override fun getInitialSize(): Dimension? {
        return Dimension(900, 600)
    }

    private fun buildListModel(): List<Feature> {
        return client.pullFeatures(project)
    }
}
