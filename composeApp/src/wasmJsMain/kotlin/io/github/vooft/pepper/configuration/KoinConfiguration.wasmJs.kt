package io.github.vooft.pepper.configuration

import pepper_ui.composeapp.generated.resources.Res

// actual fun reportsBaseUrl(): String = "https://vooft.github.io/pepper-bdd/pepper-reports"

// the reports are bundled as compose resources, so they are served by the webpack dev server as well
actual fun reportsBaseUrl(): String = Res.getUri(REPORTS_PATH).replace("/./", "/")

private const val REPORTS_PATH = "files/pepper-suites"
