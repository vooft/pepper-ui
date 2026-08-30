package io.github.vooft.pepper.components.scenariolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.lighten
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.components.utils.color
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.status

@Composable
fun PepperTestScenarioList(
    modifier: Modifier = Modifier,
    scenarios: List<PepperTestScenarioDto>,
    selectedScenario: PepperTestScenarioDto?,
    onScenarioClicked: (PepperTestScenarioDto) -> Unit = {}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(scenarios) { scenario ->
            PepperTestScenarioListItem(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                scenario = scenario,
                selected = selectedScenario?.id == scenario.id,
                onClicked = { onScenarioClicked(scenario) }
            )
        }
    }
}

@Composable
fun PepperTestScenarioListItem(
    modifier: Modifier = Modifier,
    scenario: PepperTestScenarioDto,
    selected: Boolean,
    onClicked: () -> Unit = {}
) {
    Card(
        onClick = onClicked,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = scenario.status.color.lighten()),
        border = when (selected) {
            true -> BorderStroke(3.dp, PepperColor.Black)
            false -> null
        },
    ) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Text(text = scenario.name, style = MaterialTheme.typography.labelMedium)
        }
    }
}
