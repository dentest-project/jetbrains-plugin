package tech.dentest.actions

import tech.dentest.ConfigurationDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ConfigurationAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        ConfigurationDialog(e.project).showAndGet()
    }
}
