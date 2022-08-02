package tech.dentest.settings

import tech.dentest.model.Feature

class PluginState {
    var api = "https://api.dentest.tech"
    var token: String? = null
    var destinationPath: String? = null
    var inlineParameterWrappingString: String = "\""
    var subsequentScript: String? = null
    var selectedFeaturesForPull: List<Feature> = listOf()
}
