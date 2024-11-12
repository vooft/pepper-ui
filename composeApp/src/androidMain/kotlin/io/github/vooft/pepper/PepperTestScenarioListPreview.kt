package io.github.vooft.pepper

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vooft.pepper.components.scenariolist.PepperTestScenarioList
import io.github.vooft.pepper.preview.PreviewData

@Preview
@Composable
fun PepperTestScenarioListPreview() {
    PepperTestScenarioList(scenarios = List(5) { PreviewData.aPepperTestScenario() })
}
