package io.github.vooft.pepper.components.scenariolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vooft.pepper.http.PepperReportRepository
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestSuiteDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PepperTestScenarioListViewModel(private val repository: PepperReportRepository) : ViewModel() {
    private val _state = MutableStateFlow<ModelState>(ModelState.Empty)
    val state = _state.asStateFlow()

    fun loadSuite(suiteItem: PepperRoot.PepperSuiteItem) {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            _state.value = ModelState.SuiteLoaded(suiteItem, repository.loadSuite(suiteItem.path))
        }
    }

    fun loadScenarios(suiteItem: PepperRoot.PepperSuiteItem, suite: PepperTestSuiteDto) {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            val scenarios = suite.scenarios.map {
                repository.loadScenario(suiteItem.path, it)
            }

            _state.value = ModelState.ScenariosLoaded(suite, scenarios)
        }
    }

    sealed interface ModelState {
        data object Empty : ModelState
        data class SuiteLoaded(val suiteItem: PepperRoot.PepperSuiteItem, val suite: PepperTestSuiteDto) : ModelState
        data class ScenariosLoaded(val suite: PepperTestSuiteDto, val scenarios: List<PepperTestScenarioDto>) : ModelState
        data object Loading : ModelState
    }
}
