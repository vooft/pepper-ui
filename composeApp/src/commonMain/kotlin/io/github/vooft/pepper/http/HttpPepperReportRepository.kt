package io.github.vooft.pepper.http

import io.github.vooft.pepper.http.PepperRoot.SuitePath
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestSuiteDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.Clock

class HttpPepperReportRepository(private val baseUrl: String, private val client: HttpClient) : PepperReportRepository {
    override suspend fun loadRoot(): PepperRoot {
        return client.get("$baseUrl/$ROOT_JSON?${Clock.System.now().toEpochMilliseconds()}").body()
    }

    override suspend fun loadSuite(path: SuitePath): PepperTestSuiteDto {
        return client.get("$baseUrl/${path.value}/$SUITE_JSON").body()
    }

    override suspend fun loadScenario(path: SuitePath, scenarioId: PepperTestScenarioDto.ScenarioId): PepperTestScenarioDto {
        return client.get("$baseUrl/${path.value}/${scenarioId.value}.json").body()
    }
}

private const val ROOT_JSON = "pepper-root.json"
private const val SUITE_JSON = "pepper.json"
