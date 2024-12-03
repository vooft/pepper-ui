package io.github.vooft.pepper.components.scenariolist

import androidx.compose.runtime.Composable
import io.github.vooft.compose.treeview.core.TreeView
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto

@Composable
fun PepperTestScenarioTree(scenarios: List<PepperTestScenarioDto>, onScenarioClicked: (PepperTestScenarioDto) -> Unit) {
    TreeView(
        tree = PepperScenarioTreeModel(scenarios),
        onClick = {
            when (val node = it.content) {
                is PepperScenarioNode.ScenarioNode -> onScenarioClicked(node.scenario)
                is PepperScenarioNode.TagNode -> {}
            }
        }
    )
}
