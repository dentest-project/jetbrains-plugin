package tech.dentest

import com.intellij.openapi.project.Project
import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class FeatureWriter(val project: Project) {
    fun write(features: List<Feature>?) {
        val state = PluginSettings.getInstance(project).state

        if (features == null) {
            return
        }

        for (feature in features) {
            Files.createDirectories(Paths.get("${state?.destinationPath}${extractPath(feature.path)}"))
            val file = File("${state?.destinationPath}${feature.path}")

            file.createNewFile()
            file.writeText(feature.feature)
        }
    }

    private fun extractPath(featurePath: String): String {
        return featurePath.substring(0, featurePath.lastIndexOf('/'))
    }
}
