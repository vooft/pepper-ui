package io.github.vooft.pepper

import androidx.compose.runtime.Composable
import io.github.vooft.pepper.components.frontdoor.FrontDoorScreen
import io.github.vooft.pepper.configuration.koinConfiguration
import io.github.vooft.pepper.theme.PepperTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(KoinApplication::koinConfiguration) {
        PepperTheme {
//            TypographySample(modifier = Modifier.padding(8.dp).fillMaxWidth())
            FrontDoorScreen()
        }
    }
}
