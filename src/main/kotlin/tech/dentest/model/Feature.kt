package tech.dentest.model

class Feature(val displayPath: String = "", val path: String = "", val feature: String = "") {
    override fun toString(): String {
        return displayPath
    }
}
