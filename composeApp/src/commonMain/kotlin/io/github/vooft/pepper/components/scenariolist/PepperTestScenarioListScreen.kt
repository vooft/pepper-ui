package io.github.vooft.pepper.components.scenariolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.reports.api.PepperTestScenario
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PepperTestScenarioListScreen(
    suiteItem: PepperRoot.PepperSuiteItem,
    modifier: Modifier = Modifier,
    viewModel: PepperTestScenarioListViewModel = koinViewModel(),
    onScenarioClicked: (PepperTestScenario) -> Unit = {}
) {
    val viewModelState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        when (val state = viewModelState) {
            PepperTestScenarioListViewModel.ModelState.Empty -> {
                viewModel.loadSuite(suiteItem)
            }

            PepperTestScenarioListViewModel.ModelState.Loading -> {
                Text("Loading...")
            }

            is PepperTestScenarioListViewModel.ModelState.SuiteLoaded -> {
                viewModel.loadScenarios(state.suiteItem, state.suite)
            }

            is PepperTestScenarioListViewModel.ModelState.ScenariosLoaded -> {
                PepperTestScenarioList(
                    modifier = Modifier.fillMaxSize(),
                    scenarios = state.scenarios,
                    onScenarioClicked = onScenarioClicked
                )
            }
        }
    }
}
