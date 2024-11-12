package io.github.vooft.pepper

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.vooft.pepper.components.singlesuite.SingleSuiteScreen
import io.github.vooft.pepper.configuration.koinConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(KoinApplication::koinConfiguration) {
        MaterialTheme {
            SingleSuiteScreen(modifier = Modifier.fillMaxSize())
        }
    }
}
