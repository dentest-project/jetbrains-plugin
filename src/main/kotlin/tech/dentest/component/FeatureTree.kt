package tech.dentest.component

import com.intellij.ui.treeStructure.SimpleTree
import tech.dentest.model.Feature
import tech.dentest.settings.PluginState
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class FeatureTree(private val features: List<Feature>, private val state: PluginState) {
    private val root = DefaultMutableTreeNode("\uD83C\uDFC1")
    private val tree = SimpleTree(DefaultTreeModel(root))
    private val pathToNodeMap = mutableMapOf<String, DefaultMutableTreeNode>()

    init {
        buildTree()
    }

    fun getTreeComponent(): SimpleTree {
        return tree
    }

    private fun buildTree() {
        for (feature in features) {
            addFeatureToTree(feature)
        }
        tree.expandRow(0)

        setupSelectionListener()
    }

    private fun addFeatureToTree(feature: Feature) {
        var currentNode = root
        val pathParts = feature.displayPath.split(" / ")

        var pathBuilt = ""
        for (index in pathParts.indices) {
            val part = pathParts[index]
            if (pathBuilt.isNotEmpty()) {
                pathBuilt += " / "
            }
            pathBuilt += part

            val existingNode = pathToNodeMap[pathBuilt]
            if (existingNode == null) {
                val isLeafNode = (index == pathParts.lastIndex)
                val userObject = if (isLeafNode) feature else part
                val newNode = DefaultMutableTreeNode(userObject)
                currentNode.add(newNode)
                pathToNodeMap[pathBuilt] = newNode
                currentNode = newNode
            } else {
                currentNode = existingNode
            }
        }
    }

    private fun setupSelectionListener() {
        tree.addTreeSelectionListener { e ->
            val selectedPaths = tree.selectionPaths

            state.selectedFeaturesForPull.clear()
            selectedPaths?.forEach { path ->
                val node = path.lastPathComponent as DefaultMutableTreeNode

                if (node.isLeaf) {
                    state.selectedFeaturesForPull.add(node.userObject as Feature)
                }
            }
        }
    }
}
