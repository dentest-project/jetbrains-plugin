package tech.dentest

import tech.dentest.http.Client
import tech.dentest.listeners.FeaturesDestinationButtonActionListener
import tech.dentest.settings.PluginSettings
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBComboBoxLabel
import com.intellij.ui.components.JBLabel
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JProgressBar
import javax.swing.JTextField

class ConfigurationDialog(private val project: Project?): DialogWrapper(true) {
    private val panel = JPanel(GridBagLayout())
    private val gb = GridBag()
        .setDefaultInsets(Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
        .setDefaultWeightX(1.0)
        .setDefaultFill(GridBagConstraints.HORIZONTAL)
    private val txtApi = JTextField()
    private val txtToken = JPasswordField()
    private val txtDestinationPath = JButton("Features destination", AllIcons.Actions.ProjectDirectory)
    private val txtInlineParameterWrappingString = JTextField()
    private val txtSubsequentScript = JTextField()
    private var errorLabel: JComponent? = null

    init {
        init()
        title = "Dentest Configuration"
        if (project != null) {
            val state = PluginSettings.getInstance(project).state
            txtApi.text = state.api
            txtToken.text = state.token
            txtInlineParameterWrappingString.text = state.inlineParameterWrappingString
            txtSubsequentScript.text = state.subsequentScript

            if (state.destinationPath == null) {
                state.destinationPath = "${project?.basePath}/features"
            }

            txtDestinationPath.text = state.destinationPath
        }
    }

    override fun createCenterPanel(): JComponent? {
        panel.preferredSize = Dimension(400, 200)

        panel.add(label("Dentest API"), gb.nextLine().next().weightx(0.2))
        panel.add(txtApi, gb.next().weightx(0.8))
        panel.add(label("Dentest Pull Token"), gb.nextLine().next().weightx(0.2))
        panel.add(txtToken, gb.next().weightx(0.8))
        panel.add(label("Features destination"), gb.nextLine().next().weightx(0.2))
        panel.add(txtDestinationPath, gb.next().weightx(0.8))
        panel.add(label("Inline param wrapping string"), gb.nextLine().next().weightx(0.2))
        panel.add(txtInlineParameterWrappingString, gb.next().weightx(0.8))
        panel.add(label("Subsequent script"), gb.nextLine().next().weightx(0.2))
        panel.add(txtSubsequentScript, gb.next().weightx(0.8))

        txtDestinationPath.addActionListener(FeaturesDestinationButtonActionListener(panel, txtDestinationPath, project))

        return panel
    }

    override fun doOKAction() {
        val api = txtApi.text
        val token = txtToken.text
        val inlineParameterWrappingString = txtInlineParameterWrappingString.text
        val subsequentScript = txtSubsequentScript.text
        val state = if (project != null) {
            PluginSettings.getInstance(project).state
        } else {
            null
        }

        state?.api = api
        state?.token = token
        state?.inlineParameterWrappingString = inlineParameterWrappingString
        state?.subsequentScript = subsequentScript

        val spinner = spinner()
        panel.add(spinner, gb.nextLine())
        panel.updateUI()

        val result = dryPull()

        panel.remove(spinner)

        if (result) {
            close(0)

            return
        }

        panel.add(error("The Dentest credentials are incorrect"), gb.nextLine().next().weightx(0.2))
        panel.updateUI()
    }

    private fun label(text: String): JComponent {
        val label = JBLabel(text)

        label.componentStyle = UIUtil.ComponentStyle.SMALL
        label.fontColor = UIUtil.FontColor.BRIGHTER
        label.border = JBUI.Borders.empty(0, 5, 2, 0)

        return label
    }

    private fun spinner(): JComponent {
        return JProgressBar()
    }

    private fun error(message: String): JComponent {
        if (errorLabel != null) {
            return errorLabel!!
        }

        val label = JBComboBoxLabel()

        label.text = message
        label.icon = AllIcons.General.BalloonError

        errorLabel = label

        return label
    }

    private fun dryPull(): Boolean {
        if (project == null) {
            return true
        }

        return Client.getInstance().dryPull(project)
    }
}
