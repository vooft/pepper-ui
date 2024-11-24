package io.github.vooft.pepper.configuration

import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import kotlinx.browser.window

// actual fun reportsBaseUrl(): String = "https://vooft.github.io/pepper-bdd/"

actual fun reportsBaseUrl(): String = URLBuilder(window.location.toString())
    .appendPathSegments("pepper-reports")
    .buildString()
