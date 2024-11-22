package io.github.vooft.pepper.components.pepperreport

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.vooft.pepper.components.reportsuitesstats.ReportSuitesStatsScreen
import io.github.vooft.pepper.components.singlesuite.SingleSuiteScreen
import io.github.vooft.pepper.http.PepperRoot
import io.github.vooft.pepper.model.SuiteSetViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PepperReportScreen(
    suiteItems: Collection<PepperRoot.PepperSuiteItem>,
    viewModel: SuiteSetViewModel = koinViewModel()
) {
    val viewModelState by viewModel.state.collectAsState()
    when (val state = viewModelState) {
        SuiteSetViewModel.ModelState.Empty -> {
            viewModel.loadSuites(suiteItems)
        }

        SuiteSetViewModel.ModelState.Loading -> {
            Text("Loading...")
        }

        is SuiteSetViewModel.ModelState.SuitesLoaded -> {
            if (state.suites.isEmpty()) {
                Text("No suites found")
                return
            }

            var currentSuite by remember { mutableStateOf(state.suites.first()) }

            Column(modifier = Modifier.fillMaxSize()) {
                ReportSuitesStatsScreen(
                    modifier = Modifier.fillMaxWidth().weight(4f),
                    currentSuite = currentSuite,
                    suites = state.suites,
                    onSuiteClicked = { currentSuite = it }
                )

                SingleSuiteScreen(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    suite = currentSuite
                )
            }
        }
    }
}
