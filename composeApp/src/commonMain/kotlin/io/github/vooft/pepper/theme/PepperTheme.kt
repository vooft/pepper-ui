package io.github.vooft.pepper.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PepperTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = PepperTypography()
    ) {
        content()
    }
}
