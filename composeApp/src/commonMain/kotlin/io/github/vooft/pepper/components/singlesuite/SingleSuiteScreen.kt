package io.github.vooft.pepper.components.singlesuite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.components.scenariolist.PepperTestScenarioList
import io.github.vooft.pepper.components.scenariolist.PepperTestScenarioListViewModel
import io.github.vooft.pepper.components.singlescenario.SingleScenarioScreen
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SingleSuiteScreen(modifier: Modifier = Modifier, viewModel: PepperTestScenarioListViewModel = koinViewModel(),) {
    val viewModelState by viewModel.state.collectAsState()
    when (val state = viewModelState) {
        PepperTestScenarioListViewModel.ModelState.Empty -> {
            viewModel.loadSuite(PepperRoot.PepperSuiteItem(name = "Report 1", path = "report1"))
        }

        PepperTestScenarioListViewModel.ModelState.Loading -> {
            Text("Loading...")
        }

        is PepperTestScenarioListViewModel.ModelState.SuiteLoaded -> {
            viewModel.loadScenarios(state.suiteItem, state.suite)
        }

        is PepperTestScenarioListViewModel.ModelState.ScenariosLoaded -> {
            var selectedScenario by remember { mutableStateOf(state.scenarios.firstOrNull()) }
            Row(modifier = modifier.fillMaxSize().padding(4.dp)) {
                SingleSuiteScreenLeftPane(
                    modifier = Modifier.weight(0.3f).fillMaxHeight().padding(4.dp),
                    scenarios = state.scenarios,
                    selectedScenario = selectedScenario,
                    onScenarioClicked = { selectedScenario = it }
                )

                SingleSuiteScreenRightPane(scenario = selectedScenario, modifier = Modifier.weight(0.7f).fillMaxHeight().padding(4.dp))
            }
        }
    }
}

@Composable
private fun SingleSuiteScreenLeftPane(
    modifier: Modifier = Modifier,
    scenarios: List<PepperTestScenarioDto>,
    selectedScenario: PepperTestScenarioDto?,
    onScenarioClicked: (PepperTestScenarioDto) -> Unit
) {
    PepperTestScenarioList(
        modifier = modifier,
        scenarios = scenarios,
        selectedScenario = selectedScenario,
        onScenarioClicked = onScenarioClicked
    )
}

@Composable
private fun SingleSuiteScreenRightPane(modifier: Modifier = Modifier, scenario: PepperTestScenarioDto?) {
    Box(modifier = modifier) {
        if (scenario == null) {
            Text("Select scenario")
        } else {
            SingleScenarioScreen(modifier = Modifier.fillMaxSize(), scenario = scenario)
        }
    }
}
