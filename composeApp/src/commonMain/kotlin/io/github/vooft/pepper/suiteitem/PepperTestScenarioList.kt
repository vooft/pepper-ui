package io.github.vooft.pepper.suiteitem

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.reports.api.PepperTestScenario

@Composable
fun PepperTestScenarioList(modifier: Modifier = Modifier, scenarios: List<PepperTestScenario>, onScenarioClicked: (PepperTestScenario) -> Unit = {}) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(scenarios) { scenario ->
            PepperTestScenarioListItem(scenario = scenario, onClicked = { onScenarioClicked(scenario) })
        }
    }
}
