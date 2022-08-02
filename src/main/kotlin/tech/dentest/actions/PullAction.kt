package tech.dentest.actions

import tech.dentest.ConfigurationDialog
import tech.dentest.FeatureWriter
import tech.dentest.PullDialog
import tech.dentest.http.Client
import tech.dentest.settings.PluginSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.io.File
import java.util.concurrent.TimeUnit

class PullAction: AnAction() {
    private val client = Client()
    private val writer = FeatureWriter()

    override fun actionPerformed(e: AnActionEvent) {
        val state = PluginSettings.getInstance().state

        if (state?.token == null || !dryPull()) {
            ConfigurationDialog(e.project).showAndGet()
        }

        if (!PullDialog().showAndGet()) {
            return
        }

        writer.write(state?.selectedFeaturesForPull)

        state?.selectedFeaturesForPull = listOf()

        if (state?.subsequentScript != null && !state.subsequentScript!!.isEmpty()) {
            val arguments = state.subsequentScript!!.split(' ').toTypedArray()

            ProcessBuilder(*arguments).directory(File(e.project?.basePath!!)).start().also { it.waitFor(60, TimeUnit.SECONDS) }
        }
    }

    private fun dryPull(): Boolean {
        return client.dryPull()
    }
}
