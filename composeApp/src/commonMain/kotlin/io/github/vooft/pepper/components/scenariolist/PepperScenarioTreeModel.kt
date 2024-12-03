package io.github.vooft.pepper.components.scenariolist

import androidx.compose.runtime.Composable
import io.github.vooft.compose.treeview.core.node.Branch
import io.github.vooft.compose.treeview.core.node.Leaf
import io.github.vooft.compose.treeview.core.tree.Tree
import io.github.vooft.compose.treeview.core.tree.TreeScope
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto.ScenarioTag

@Composable
fun PepperScenarioTreeModel(scenarios: List<PepperTestScenarioDto>): Tree<PepperScenarioNode> = Tree {
    for (node in PepperScenarioNode.create(scenarios)) {
        PepperTreeNode(node)
    }
}

@Composable
fun TreeScope.PepperTreeNode(node: PepperScenarioNode) {
    when (node) {
        is PepperScenarioNode.ScenarioNode -> {
            Leaf(
                content = node,
                name = node.scenario.name
            )
        }

        is PepperScenarioNode.TagNode -> {
            Branch(
                content = node,
                name = node.tag.value
            ) {
                for (child in node.children) {
                    PepperTreeNode(child)
                }
            }
        }
    }
}

sealed interface PepperScenarioNode {
    data class TagNode(val tag: ScenarioTag, val children: List<PepperScenarioNode>) : PepperScenarioNode
    data class ScenarioNode(val scenario: PepperTestScenarioDto) : PepperScenarioNode

    companion object {
        fun create(scenarios: List<PepperTestScenarioDto>): List<PepperScenarioNode> {
            val trie = TagTrie()
            scenarios.forEach { trie.addScenario(it) }

            return trie.toPepperScenarioNodes()
        }
    }
}

private class TagTrie {
    val rootTags = mutableMapOf<ScenarioTag, TagNode>()
    val uncategorized = TagNode(ScenarioTag("Uncategorized"))

    inner class TagNode(val tag: ScenarioTag) {
        val children = mutableMapOf<ScenarioTag, TagNode>()
        val scenarios = mutableListOf<PepperTestScenarioDto>()

        fun toPepperScenarioNode(): PepperScenarioNode {
            val children = children.values.map { it.toPepperScenarioNode() }
            val scenarios = scenarios.map { PepperScenarioNode.ScenarioNode(it) }

            return PepperScenarioNode.TagNode(tag, children + scenarios)
        }
    }

    fun addScenario(scenario: PepperTestScenarioDto) {
        if (scenario.tags.isEmpty()) {
            uncategorized.scenarios.add(scenario)
            return
        }

        var current = rootTags.getOrPut(scenario.tags.first()) { TagNode(scenario.tags.first()) }

        for (tag in scenario.tags.drop(1)) {
            current = current.children.getOrPut(tag) { TagNode(tag) }
        }

        current.scenarios.add(scenario)
    }

    fun toPepperScenarioNodes(): List<PepperScenarioNode> {
        return rootTags.values.map { it.toPepperScenarioNode() } + uncategorized.toPepperScenarioNode()
    }
}
