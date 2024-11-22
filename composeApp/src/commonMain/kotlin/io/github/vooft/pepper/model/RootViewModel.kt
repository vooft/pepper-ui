package io.github.vooft.pepper.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vooft.pepper.http.PepperReportRepository
import io.github.vooft.pepper.http.PepperRoot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RootViewModel(private val repository: PepperReportRepository) : ViewModel() {
    private val _state = MutableStateFlow<ModelState>(ModelState.Empty)
    val state = _state.asStateFlow()

    fun loadRoot() {
        viewModelScope.launch {
            _state.value = ModelState.Loading
            _state.value = ModelState.RootLoaded(repository.loadRoot())
        }
    }

    sealed interface ModelState {
        data object Empty : ModelState
        data class RootLoaded(val root: PepperRoot) : ModelState
        data object Loading : ModelState
    }
}
