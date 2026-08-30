package io.github.vooft.pepper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TypographySample(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text("DisplayLarge", style = MaterialTheme.typography.displayLarge)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("DisplayMedium", style = MaterialTheme.typography.displayMedium)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("DisplaySmall", style = MaterialTheme.typography.displaySmall)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("HeadlineLarge", style = MaterialTheme.typography.headlineLarge)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("HeadlineMedium", style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("HeadlineSmall", style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("TitleLarge", style = MaterialTheme.typography.titleLarge)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("TitleMedium", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("TitleSmall", style = MaterialTheme.typography.titleSmall)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("BodyLarge", style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("BodyMedium", style = MaterialTheme.typography.bodyMedium)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("BodySmall", style = MaterialTheme.typography.bodySmall)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("LabelLarge", style = MaterialTheme.typography.labelLarge)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("LabelMedium", style = MaterialTheme.typography.labelMedium)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        Text("LabelSmall", style = MaterialTheme.typography.labelSmall)
    }
}
