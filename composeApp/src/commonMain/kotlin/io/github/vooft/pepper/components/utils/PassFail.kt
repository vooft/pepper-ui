package io.github.vooft.pepper.components.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

@Composable
fun PassFailChip(modifier: Modifier = Modifier, status: PepperTestStatus) {
    AssistChip(
        modifier = modifier,
        onClick = {},
        label = {
            Text(
                text = when (status) {
                    PepperTestStatus.PASSED -> "PASS"
                    PepperTestStatus.FAILED -> "FAIL"
                    PepperTestStatus.SKIPPED -> "SKIP"
                },
                fontFamily = FontFamily.Monospace
            )
        },
        leadingIcon = { PassFailIcon(status = status) },
        border = null,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = status.color.copy(alpha = 0.2f),
            labelColor = status.color,
            leadingIconContentColor = status.color
        )
    )
}

val PepperTestStatus.color get() = when (this) {
    PepperTestStatus.PASSED -> PepperColor.Green
    PepperTestStatus.FAILED -> PepperColor.Red
    PepperTestStatus.SKIPPED -> PepperColor.Grey
}
