package tech.dentest.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import tech.dentest.model.Feature
import tech.dentest.settings.PluginSettings
import com.intellij.util.io.encodeUrlQueryParameter
import okhttp3.*

class Client {
    private val client = OkHttpClient()

    fun dryPull(): Boolean {
        val response = client.newCall(getGetRequest("pull/paths")).execute();
        response.body?.close()

        return response.code == 200
    }

    fun pullPaths(): List<String> {
        val response = client.newCall(getGetRequest("pull/paths")).execute();
        val contents = response.body?.string().toString()
        response.body?.close()

        return ObjectMapper().readValue(contents, object : TypeReference<List<String>>(){})
    }

    fun pullFeatures(): List<Feature> {
        val request = getGetRequest("pull/features?inlineParameterWrapper=${PluginSettings.getInstance().state?.inlineParameterWrappingString?.encodeUrlQueryParameter()}")
        val response = client.newCall(request).execute();
        val contents = response.body?.string().toString()
        response.body?.close()

        return ObjectMapper().readValue(contents, object : TypeReference<List<Feature>>(){})
    }

    private fun getGetRequest(url: String): Request {
        val state = PluginSettings.getInstance().state

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
