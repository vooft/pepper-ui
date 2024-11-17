package io.github.vooft.pepper.components.singlesuite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.components.scenariolist.PepperTestScenarioListScreen
import io.github.vooft.pepper.components.singlescenario.SingleScenarioScreen
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.reports.api.PepperTestScenario

@Composable
fun SingleSuiteScreen(modifier: Modifier = Modifier) {
    val suiteItem = PepperRoot.PepperSuiteItem(name = "Sample", path = "sample-report")

    var selectedScenario by remember { mutableStateOf<PepperTestScenario?>(null) }
    Row(modifier = modifier.fillMaxSize().padding(4.dp)) {
        PepperTestScenarioListScreen(suiteItem = suiteItem, modifier = Modifier.weight(0.3f).fillMaxHeight().padding(4.dp)) {
            selectedScenario = it
        }

        SingleSuiteScreenRightPane(scenario = selectedScenario, modifier = Modifier.weight(0.7f).fillMaxHeight().padding(4.dp))
    }
}

@Composable
private fun SingleSuiteScreenRightPane(modifier: Modifier = Modifier, scenario: PepperTestScenario?) {
    Box(modifier = modifier) {
        if (scenario == null) {
            Text("Select scenario")
        } else {
            SingleScenarioScreen(modifier = Modifier.fillMaxSize(), scenario = scenario)
        }
    }
}
