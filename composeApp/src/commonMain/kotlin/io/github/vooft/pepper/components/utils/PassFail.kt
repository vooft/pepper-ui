package io.github.vooft.pepper.components.utils

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import io.github.vooft.pepper.reports.api.PepperScenarioStatus

@Composable
fun PassFailIcon(modifier: Modifier = Modifier, status: PepperScenarioStatus) {
    Icon(
        imageVector = when (status) {
            PepperScenarioStatus.PASSED -> Icons.Default.Check
            PepperScenarioStatus.FAILED -> Icons.Default.Close
        },
        contentDescription = null,
        tint = status.color
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PassFailChip(modifier: Modifier = Modifier, status: PepperScenarioStatus) {
    Chip(
        modifier = modifier,
        onClick = {},
        leadingIcon = { PassFailIcon(status = status) },
        colors = ChipDefaults.chipColors(
            backgroundColor = status.color.copy(alpha = 0.2f),
            contentColor = status.color
        )
    ) {
        Text(
            text = when (status) {
                PepperScenarioStatus.PASSED -> "PASS"
                PepperScenarioStatus.FAILED -> "FAIL"
            },
            fontFamily = FontFamily.Monospace
        )
    }
}

val PepperScenarioStatus.color get() = when (this) {
    PepperScenarioStatus.PASSED -> PepperColor.Green
    PepperScenarioStatus.FAILED -> PepperColor.Red
}
