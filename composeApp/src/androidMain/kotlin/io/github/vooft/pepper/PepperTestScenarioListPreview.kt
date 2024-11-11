package io.github.vooft.pepper

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vooft.pepper.preview.PreviewData
import io.github.vooft.pepper.suiteitem.PepperTestScenarioList

@Preview
@Composable
fun PepperTestScenarioListPreview() {
    PepperTestScenarioList(scenarios = List(5) { PreviewData.aPepperTestScenario() })
}
