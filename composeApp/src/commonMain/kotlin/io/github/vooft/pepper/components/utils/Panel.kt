package io.github.vooft.pepper.components.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Panel(modifier: Modifier = Modifier, title: String? = null, content: @Composable () -> Unit) {
    Card(
        modifier = modifier.padding(4.dp),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            if (title != null) {
                Text(title, style = MaterialTheme.typography.h4)
                Divider()
            }

            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                content()
            }
        }
    }
}
