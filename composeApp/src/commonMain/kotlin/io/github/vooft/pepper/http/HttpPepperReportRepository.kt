package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestSuite
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HttpPepperReportRepository(private val baseUrl: String, private val client: HttpClient) : PepperReportRepository {
    override suspend fun loadRoot(): PepperRoot {
        return client.get("$baseUrl/$ROOT_JSON").body()
    }

    override suspend fun loadSuite(path: String): PepperTestSuite {
        return client.get("$baseUrl/$path/$SUITE_JSON").body()
    }

    override suspend fun loadScenario(path: String, scenarioId: String): PepperTestScenario {
        return client.get("$baseUrl/$path/$scenarioId.json").body()
    }
}

private const val ROOT_JSON = "pepper-root.json"
private const val SUITE_JSON = "pepper.json"
