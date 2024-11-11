package io.github.vooft.pepper

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vooft.pepper.preview.PreviewData.aPepperTestScenario
import io.github.vooft.pepper.suiteitem.PepperTestScenarioListItem

@Preview
@Composable
fun PepperScenarioListItemPreview() {
    PepperTestScenarioListItem(scenario = aPepperTestScenario())
}
