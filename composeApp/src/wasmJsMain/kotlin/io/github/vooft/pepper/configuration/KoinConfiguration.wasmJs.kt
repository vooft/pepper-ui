package io.github.vooft.pepper.configuration

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URL
import pepper_ui.composeapp.generated.resources.Res

actual fun reportsBaseUrl(): String = if (window.location.hostname in LOCAL_HOSTNAMES) {
    // during development the sample reports are bundled as compose resources,
    // so they are served by the webpack dev server as well
    Res.getUri(BUNDLED_REPORTS_PATH).replace("/./", "/")
} else {
    // in production the reports are plain files published next to the app;
    // resolving against the document base keeps it working under a sub-path, e.g. /pepper-bdd/
    URL(PRODUCTION_REPORTS_PATH, document.baseURI).href
}

private val LOCAL_HOSTNAMES = setOf("localhost", "127.0.0.1", "[::1]", "0.0.0.0")

private const val BUNDLED_REPORTS_PATH = "files/pepper-suites"
private const val PRODUCTION_REPORTS_PATH = "pepper-reports"
