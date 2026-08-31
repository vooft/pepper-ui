package io.github.vooft.pepper

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vooft.pepper.components.frontdoor.FrontDoorScreen
import io.github.vooft.pepper.configuration.pepperKoinConfiguration
import io.github.vooft.pepper.theme.PepperTheme
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(pepperKoinConfiguration) {
        PepperTheme {
//            TypographySample(modifier = Modifier.padding(8.dp).fillMaxWidth())
            FrontDoorScreen()
        }
    }
}
