package io.github.vooft.pepper.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vooft.pepper.http.LoadablePepperSuite
import io.github.vooft.pepper.http.PepperReportRepository
import io.github.vooft.pepper.http.loadScenarios
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SingleSuiteViewModel(private val repository: PepperReportRepository) : ViewModel() {
    private val _state = MutableStateFlow<ModelState>(ModelState.Empty)
    val state = _state.asStateFlow()

    fun loadScenarios(loadablePepperSuite: LoadablePepperSuite) {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            val scenarios = repository.loadScenarios(loadablePepperSuite.suiteItem.path, loadablePepperSuite.suite.scenarios.map { it.id })

            _state.value = ModelState.ScenariosLoaded(loadablePepperSuite, scenarios)
        }
    }

    sealed interface ModelState {
        data object Empty : ModelState
        data class ScenariosLoaded(val suite: LoadablePepperSuite, val scenarios: List<PepperTestScenarioDto>) : ModelState
        data object Loading : ModelState
    }
}
