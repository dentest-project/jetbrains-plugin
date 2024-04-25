package tech.dentest.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.intellij.openapi.project.Project
import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import okhttp3.*
import java.net.URLEncoder

class Client {
    private val client = OkHttpClient()

    fun dryPull(project: Project): Boolean {
        val response = client.newCall(getGetRequest(project, "pull/paths")).execute();
        response.body?.close()

        return response.code == 200
    }

    fun pullFeatures(project: Project): List<Feature> {
        val request = getGetRequest(project, "pull/features?inlineParameterWrapper=${URLEncoder.encode(PluginSettings.getInstance(project).state?.inlineParameterWrappingString, "utf-8")}")
        val response = client.newCall(request).execute();
        val contents = response.body?.string().toString()
        response.body?.close()

        return ObjectMapper().readValue(contents, object : TypeReference<List<Feature>>(){})
    }

    private fun getGetRequest(project: Project, url: String): Request {
        val state = PluginSettings.getInstance(project).state

        return Request
            .Builder()
            .url("${state?.api}/$url")
            .addHeader("Authorization", "Pull ${state?.token}")
            .get()
            .build()
    }

    companion object {
        private val client = Client()

        @JvmStatic
        fun getInstance(): Client {
            return client
        }
    }
}
