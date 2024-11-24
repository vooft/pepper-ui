package io.github.vooft.pepper

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.components.utils.Panel
import io.github.vooft.pepper.components.utils.PepperColor

@Preview
@Composable
fun PanelPreview() {
    Box(modifier = Modifier.fillMaxSize().background(PepperColor.Grey100)) {
        Panel(modifier = Modifier.size(200.dp)) {
            Text("Hello, World!")
        }
    }
}
