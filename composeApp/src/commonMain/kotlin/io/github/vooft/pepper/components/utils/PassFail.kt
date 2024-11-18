package io.github.vooft.pepper.components.utils

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import io.github.vooft.pepper.reports.api.PepperTestStatus

@Composable
fun PassFailIcon(modifier: Modifier = Modifier, status: PepperTestStatus) {
    Icon(
        modifier = modifier,
        imageVector = when (status) {
            PepperTestStatus.PASSED -> Icons.Default.Check
            PepperTestStatus.FAILED -> Icons.Default.Close
            PepperTestStatus.SKIPPED -> Icons.Default.Cancel
        },
        contentDescription = null,
        tint = status.color
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PassFailChip(modifier: Modifier = Modifier, status: PepperTestStatus) {
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
                PepperTestStatus.PASSED -> "PASS"
                PepperTestStatus.FAILED -> "FAIL"
                PepperTestStatus.SKIPPED -> "SKIP"
            },
            fontFamily = FontFamily.Monospace
        )
    }
}

val PepperTestStatus.color get() = when (this) {
    PepperTestStatus.PASSED -> PepperColor.Green
    PepperTestStatus.FAILED -> PepperColor.Red
    PepperTestStatus.SKIPPED -> PepperColor.Grey
}
