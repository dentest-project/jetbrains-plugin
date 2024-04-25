package tech.dentest.listeners

import com.intellij.openapi.project.Project
import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import com.intellij.ui.components.JBList
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class FeatureListSelectionListener(val project: Project): ListSelectionListener {
    override fun valueChanged(e: ListSelectionEvent?) {
        val list = (e?.source) as JBList<Feature>

        val state = PluginSettings.getInstance(project).state

        state?.selectedFeaturesForPull = list.selectedValuesList
    }
}
