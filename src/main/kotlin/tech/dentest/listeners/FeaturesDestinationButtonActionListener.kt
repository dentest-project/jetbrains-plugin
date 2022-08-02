package tech.dentest.listeners

import tech.dentest.settings.PluginSettings
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.project.Project
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel

class FeaturesDestinationButtonActionListener (private val panel: JPanel, private val trigger: JButton, private val project: Project?): ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        val fileChooserDescriptor = FileChooserDescriptor(false, true, false, false, false, false)

        fileChooserDescriptor.title = "Features Destination"

        FileChooser.chooseFile(fileChooserDescriptor, project, null) {
            val state = PluginSettings.getInstance().state

            trigger.text = it.path
            state?.destinationPath = it.path
        }

        panel.updateUI()
    }
}
