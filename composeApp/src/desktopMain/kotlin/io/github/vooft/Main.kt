package io.github.vooft

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vooft.pepper.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pepper-ui",
    ) {
        App()
    }
}
