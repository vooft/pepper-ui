package io.github.vooft.pepper

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import io.github.vooft.pepper.components.scenariolist.PepperTestScenarioList
import io.github.vooft.pepper.preview.PreviewData

@Preview
@Composable
fun PepperTestScenarioListPreview() {
    PepperTestScenarioList(scenarios = List(5) { PreviewData.aPepperTestScenario() })
}
