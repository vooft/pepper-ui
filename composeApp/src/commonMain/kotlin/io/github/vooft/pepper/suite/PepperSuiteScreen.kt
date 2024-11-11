package io.github.vooft.pepper.suite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.vooft.pepper.suiteitem.PepperTestScenarioList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PepperSuiteScreen(viewModel: PepperSuiteViewModel = koinViewModel()) {
    val viewModelState by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = viewModelState) {
            PepperSuiteViewModel.ModelState.Empty -> {
                viewModel.loadRoot()
            }

            PepperSuiteViewModel.ModelState.Loading -> {
                Text("Loading...")
            }

            is PepperSuiteViewModel.ModelState.RootLoaded -> {
                val first = state.root.suites.firstOrNull()
                if (first == null) {
                    Text("No suites found")
                } else {
                    viewModel.loadSuite(first)
                }
            }

            is PepperSuiteViewModel.ModelState.SuiteLoaded -> {
                viewModel.loadScenarios(state.suiteItem, state.suite)
            }

            is PepperSuiteViewModel.ModelState.ScenariosLoaded -> {
                PepperTestScenarioList(modifier = Modifier.fillMaxSize(), scenarios = state.scenarios)
            }
        }
    }
}
