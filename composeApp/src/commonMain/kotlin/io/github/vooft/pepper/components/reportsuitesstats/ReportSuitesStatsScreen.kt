package io.github.vooft.pepper.components.reportsuitesstats

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.vooft.pepper.http.LoadablePepperSuite

@Composable
fun ReportSuitesStatsScreen(
    modifier: Modifier = Modifier,
    suites: List<LoadablePepperSuite>,
    currentSuite: LoadablePepperSuite?,
    onSuiteClicked: (LoadablePepperSuite) -> Unit
) {
    Text("Boo fucking hoo")
}
