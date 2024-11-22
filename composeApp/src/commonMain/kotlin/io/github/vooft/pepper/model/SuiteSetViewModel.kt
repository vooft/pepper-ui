package io.github.vooft.pepper.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vooft.pepper.http.LoadablePepperSuite
import io.github.vooft.pepper.http.PepperReportRepository
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.http.loadSuites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SuiteSetViewModel(private val repository: PepperReportRepository) : ViewModel() {
    private val _state = MutableStateFlow<ModelState>(ModelState.Empty)
    val state = _state.asStateFlow()

    fun loadSuites(suites: Collection<PepperRoot.PepperSuiteItem>) {
        viewModelScope.launch {
            _state.value = ModelState.Loading

            val loaded = repository.loadSuites(suites.map { it.path })

            _state.value = ModelState.SuitesLoaded(
                suites.map {
                    val suite = loaded[it.path] ?: error("Suite ${it.path} not found")
                    LoadablePepperSuite(it, suite)
                }
            )
        }
    }

    sealed interface ModelState {
        data object Empty : ModelState
        data class SuitesLoaded(val suites: List<LoadablePepperSuite>) : ModelState
        data object Loading : ModelState
    }
}
