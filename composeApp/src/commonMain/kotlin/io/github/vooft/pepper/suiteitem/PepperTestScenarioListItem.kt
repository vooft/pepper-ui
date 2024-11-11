package io.github.vooft.pepper.suiteitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.reports.api.PepperScenarioStatus
import io.github.vooft.pepper.reports.api.PepperTestScenario

@Composable
fun PepperTestScenarioListItem(modifier: Modifier = Modifier, scenario: PepperTestScenario) {
    Card(
        backgroundColor = scenario.status.color,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ScenarioListItemContent(scenario = scenario)
    }
}

@Composable
fun ScenarioListItemContent(modifier: Modifier = Modifier, scenario: PepperTestScenario) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(scenario.className)
        Text(scenario.name)
    }
}

private val PepperScenarioStatus.color: Color get() = when (this) {
    PepperScenarioStatus.PASSED -> Color.Green
    PepperScenarioStatus.FAILED -> Color.Red
}
