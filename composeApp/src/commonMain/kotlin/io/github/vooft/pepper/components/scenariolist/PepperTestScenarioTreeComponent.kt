package io.github.vooft.pepper.components.scenariolist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.vooft.compose.treeview.core.TreeView
import io.github.vooft.compose.treeview.core.TreeViewStyle
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto

@Composable
fun PepperTestScenarioTreeComponent(
    modifier: Modifier = Modifier,
    scenarios: List<PepperTestScenarioDto>,
    selectedScenario: PepperTestScenarioDto,
    onScenarioClicked: (PepperTestScenarioDto) -> Unit
) {
    val tree = PepperTestScenarioTree(scenarios = scenarios, selectedScenarioId = selectedScenario.id)

    // expand tree on startup
    LaunchedEffect(Unit) { tree.expandAll() }

    TreeView(
        modifier = modifier,
        tree = tree,
        onClick = {
            when (val node = it.content) {
                is PepperScenarioNode.ScenarioNode -> onScenarioClicked(node.scenario)
                is PepperScenarioNode.TagNode -> {}
            }
        },
        style = TreeViewStyle(
            nodePadding = PaddingValues(1.dp),
            nodeNameTextStyle = TextStyle.Default
        )
    )
}
