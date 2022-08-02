package tech.dentest.listeners

import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import com.intellij.ui.components.JBList
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class FeatureListSelectionListener: ListSelectionListener {
    override fun valueChanged(e: ListSelectionEvent?) {
        val list = (e?.source) as JBList<Feature>

        val state = PluginSettings.getInstance().state

        state?.selectedFeaturesForPull = list.selectedValuesList
    }
}
