package io.github.vooft.pepper.components.frontdoor

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.vooft.pepper.components.pepperreport.PepperReportScreen
import io.github.vooft.pepper.model.RootViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FrontDoorScreen(
    viewModel: RootViewModel = koinViewModel()
) {
    val viewModelState by viewModel.state.collectAsState()
    when (val state = viewModelState) {
        RootViewModel.ModelState.Empty -> {
            viewModel.loadRoot()
        }
        RootViewModel.ModelState.Loading -> {
            Text("Loading...")
        }
        is RootViewModel.ModelState.RootLoaded -> {
            PepperReportScreen(suiteItems = state.root.suites.takeLast(LAST_SUITES_DISPLAY))
        }
    }
}

private const val LAST_SUITES_DISPLAY = 20
