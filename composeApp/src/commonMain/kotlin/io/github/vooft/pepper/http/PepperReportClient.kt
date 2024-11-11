package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestSuite
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

class PepperReportClient(private val baseUrl: String, private val client: HttpClient) {
    suspend fun loadRoot(): PepperRoot {
        return client.get("$baseUrl/$ROOT_JSON").body()
    }

    suspend fun loadSuite(path: String): PepperTestSuite {
        return client.get("$baseUrl/$path/$SUITE_JSON").body()
    }

    suspend fun loadScenario(path: String, scenarioId: String): PepperTestScenario {
        return client.get("$baseUrl/$path/$scenarioId.json").body()
    }
}

@Serializable
data class PepperRoot(
    val suites: List<PepperSuiteItem>
) {
    @Serializable
    data class PepperSuiteItem(
        val name: String,
        val path: String
    )
}

private const val ROOT_JSON = "pepper-root.json"
private const val SUITE_JSON = "pepper.json"
