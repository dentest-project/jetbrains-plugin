package tech.dentest.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "Dentest",
    storages = [Storage("dentest.xml")]
)
class PluginSettings: PersistentStateComponent<PluginState> {
    private var pluginState = PluginState()

    override fun getState(): PluginState? {
        return pluginState
    }

    override fun loadState(state: PluginState) {
        pluginState = state
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersistentStateComponent<PluginState> {
            return ServiceManager.getService(PluginSettings::class.java)
        }
    }
}
