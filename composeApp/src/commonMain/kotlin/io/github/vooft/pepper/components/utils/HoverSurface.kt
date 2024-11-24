package io.github.vooft.pepper.components.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HoverSurface(modifier: Modifier = Modifier, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = Color.LightGray,
        modifier = modifier.padding(8.dp),
    ) {
        Box(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
            content()
        }
    }
}
