package io.github.vooft.pepper.components.singlesuite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import io.github.vooft.pepper.components.singlescenario.SingleScenarioScreen
import io.github.vooft.pepper.components.utils.Panel
import io.github.vooft.pepper.http.LoadablePepperSuite
import io.github.vooft.pepper.model.SingleSuiteViewModel
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SingleSuiteScreen(
    modifier: Modifier = Modifier,
    suite: LoadablePepperSuite,
    viewModel: SingleSuiteViewModel = koinViewModel()
) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModelState by viewModel.state.collectAsState()
        when (val state = viewModelState) {
            SingleSuiteViewModel.ModelState.Empty -> {
                viewModel.loadScenarios(suite)
            }

            SingleSuiteViewModel.ModelState.Loading -> {
                Text("Loading...")
            }

            is SingleSuiteViewModel.ModelState.ScenariosLoaded -> {
                if (state.suite != suite) {
                    viewModel.loadScenarios(suite)
                    return
                }

                var selectedScenario by remember { mutableStateOf(state.scenarios.firstOrNull()) }
                Row(modifier = Modifier.padding(4.dp)) {
                    Panel(
                        modifier = Modifier.weight(0.3f).fillMaxHeight(),
                        title = "Scenarios"
                    ) {
                        SingleSuiteScreenLeftPane(
                            modifier = Modifier.fillMaxSize(),
                            scenarios = state.scenarios,
                            selectedScenario = selectedScenario,
                            onScenarioClicked = { selectedScenario = it }
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Panel(modifier = Modifier.weight(0.7f).fillMaxHeight()) {
                        SingleSuiteScreenRightPane(
                            modifier = Modifier.fillMaxSize(),
                            scenario = selectedScenario
                        )
                    }
                }
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
