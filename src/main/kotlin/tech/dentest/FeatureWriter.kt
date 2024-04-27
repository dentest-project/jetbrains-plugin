package tech.dentest

import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class FeatureWriter(val project: Project) {
    fun write(features: List<Feature>?) {
        val state = PluginSettings.getInstance(project).state

        if (features.isNullOrEmpty()) {
            return
        }

        for (feature in features) {
            Files.createDirectories(Paths.get("${state.destinationPath}${extractPath(feature.path)}"))
            val file = File("${state.destinationPath}${feature.path}")

            file.createNewFile()
            file.writeText(feature.feature)
        }

        val file = File("${state.destinationPath}${features.first().path}")
        val virtualFile: VirtualFile? = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file)

        virtualFile ?: return
        FileEditorManager.getInstance(project).openFile(virtualFile, true)

        val psiFile: PsiFile? = PsiManager.getInstance(project).findFile(virtualFile)

        psiFile?.let {
            ProjectView.getInstance(project).selectPsiElement(it, true)
        }
    }

    private fun extractPath(featurePath: String): String {
        return featurePath.substring(0, featurePath.lastIndexOf('/'))
    }
}
