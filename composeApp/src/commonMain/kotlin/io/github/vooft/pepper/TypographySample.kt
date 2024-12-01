package io.github.vooft.pepper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TypographySample(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("H1", style = MaterialTheme.typography.h1)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("H2", style = MaterialTheme.typography.h2)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("H3", style = MaterialTheme.typography.h3)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("H4", style = MaterialTheme.typography.h4)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("H5", style = MaterialTheme.typography.h5)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("H6", style = MaterialTheme.typography.h6)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Subtitle1", style = MaterialTheme.typography.subtitle1)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Subtitle2", style = MaterialTheme.typography.subtitle2)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Body1", style = MaterialTheme.typography.body1)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Body2", style = MaterialTheme.typography.body2)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Button", style = MaterialTheme.typography.button)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Caption", style = MaterialTheme.typography.caption)
        Divider(modifier = Modifier.fillMaxWidth())

        Text("Overline", style = MaterialTheme.typography.overline)
    }
}
