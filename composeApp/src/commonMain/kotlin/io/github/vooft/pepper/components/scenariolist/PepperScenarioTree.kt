package io.github.vooft.pepper.components.scenariolist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import io.github.vooft.compose.treeview.core.node.Branch
import io.github.vooft.compose.treeview.core.node.Leaf
import io.github.vooft.compose.treeview.core.tree.Tree
import io.github.vooft.compose.treeview.core.tree.TreeScope
import io.github.vooft.pepper.components.utils.PassFailIcon
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto.ScenarioId
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto.ScenarioTag
import io.github.vooft.pepper.reports.api.status

@Composable
fun PepperScenarioTree(scenarios: List<PepperTestScenarioDto>, selectedScenarioId: ScenarioId): Tree<PepperScenarioNode> = Tree {
    for (node in PepperScenarioNode.create(scenarios)) {
        PepperTreeNode(node, selectedScenarioId)
    }
}

@Composable
fun TreeScope.PepperTreeNode(node: PepperScenarioNode, selectedScenarioId: ScenarioId) {
    when (node) {
        is PepperScenarioNode.ScenarioNode -> {
            Leaf(
                content = node,
                name = node.scenario.name,
                customName = {
                    Text(
                        text = node.scenario.name,
                        style = when (node.scenario.id) {
                            selectedScenarioId -> TextStyle.Default.copy(fontWeight = FontWeight.Bold)
                            else -> TextStyle.Default
                        },
                        modifier = Modifier
                    )
                },
                customIcon = {
                    PassFailIcon(status = node.scenario.status)
                }
            )
        }

        is PepperScenarioNode.TagNode -> {
            Branch(
                content = node,
                name = node.tag.value,
                customName = {
                    Text(
                        text = node.tag.value,
                        style = TextStyle.Default.copy(color = PepperColor.Grey600),
                        modifier = Modifier
                    )
                }
            ) {
                for (child in node.children) {
                    PepperTreeNode(child, selectedScenarioId)
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
