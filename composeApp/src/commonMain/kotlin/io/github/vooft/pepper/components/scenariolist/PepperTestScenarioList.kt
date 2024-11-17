package io.github.vooft.pepper.components.scenariolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.lighten
import io.github.vooft.pepper.components.utils.color
import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.status

@Composable
fun PepperTestScenarioList(
    modifier: Modifier = Modifier,
    scenarios: List<PepperTestScenario>,
    onScenarioClicked: (PepperTestScenario) -> Unit = {}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(scenarios) { scenario ->
            PepperTestScenarioListItem(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                scenario = scenario,
                onClicked = { onScenarioClicked(scenario) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PepperTestScenarioListItem(modifier: Modifier = Modifier, scenario: PepperTestScenario, onClicked: () -> Unit = {}) {
    Card(
        backgroundColor = scenario.status.color.lighten(),
        modifier = modifier,
        onClick = onClicked,
    ) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Text(scenario.className)
            Text(scenario.name)
        }
    }
}
