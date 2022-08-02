package tech.dentest

import tech.dentest.http.Client
import tech.dentest.listeners.FeatureListSelectionListener
import tech.dentest.model.Feature
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBList
import javax.swing.JComponent

class PullDialog: DialogWrapper(true) {
    private val client = Client.getInstance()

    init {
        init()
        title = "Dentest Pull"
    }

    override fun createCenterPanel(): JComponent {
        val list = JBList(buildListModel())

        list.addListSelectionListener(FeatureListSelectionListener())

        return list
    }

    private fun buildListModel(): List<Feature> {
        return client.pullFeatures()
    }
}
