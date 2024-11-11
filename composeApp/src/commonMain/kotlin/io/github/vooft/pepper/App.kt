package io.github.vooft.pepper

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.vooft.pepper.configuration.koinConfiguration
import io.github.vooft.pepper.suite.PepperSuiteScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(KoinApplication::koinConfiguration) {
        MaterialTheme {
            PepperSuiteScreen()
        }
    }
}