package io.github.vooft.pepper.suite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vooft.pepper.http.PepperReportClient
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestSuite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PepperSuiteViewModel(private val client: PepperReportClient) : ViewModel() {
    private val _state = MutableStateFlow<ModelState>(ModelState.Empty)
    val state = _state.asStateFlow()

    fun loadRoot() {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            _state.value = ModelState.RootLoaded(client.loadRoot())
        }
    }

    fun loadSuite(suiteItem: PepperRoot.PepperSuiteItem) {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            _state.value = ModelState.SuiteLoaded(suiteItem, client.loadSuite(suiteItem.path))
        }
    }

    fun loadScenarios(suiteItem: PepperRoot.PepperSuiteItem, suite: PepperTestSuite) {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            val scenarios = suite.scenarios.map {
                client.loadScenario(suiteItem.path, it)
            }

            _state.value = ModelState.ScenariosLoaded(suite, scenarios)
        }
    }

    sealed interface ModelState {
        data object Empty : ModelState
        data class RootLoaded(val root: PepperRoot) : ModelState
        data class SuiteLoaded(val suiteItem: PepperRoot.PepperSuiteItem, val suite: PepperTestSuite) : ModelState
        data class ScenariosLoaded(val suite: PepperTestSuite, val scenarios: List<PepperTestScenario>) : ModelState
        data object Loading : ModelState
    }
}

