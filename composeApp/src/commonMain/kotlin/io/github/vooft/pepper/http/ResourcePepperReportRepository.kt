package io.github.vooft.pepper.http

import io.github.vooft.pepper.http.PepperRoot.SuitePath
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto.ScenarioId
import io.github.vooft.pepper.reports.api.PepperTestSuiteDto
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pepper_ui.composeapp.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
class ResourcePepperReportRepository : PepperReportRepository {
    override suspend fun loadRoot(): PepperRoot {
        val json = Res.readBytes("files/pepper-suites/pepper-root.json").decodeToString()
        println("root: $json")
        return Json.decodeFromString(json)
    }

    override suspend fun loadSuite(path: SuitePath): PepperTestSuiteDto {
        val json = Res.readBytes("files/pepper-suites/${path.value}/pepper.json").decodeToString()
        println("suite: $json")
        return Json.decodeFromString(json)
    }

    override suspend fun loadScenario(path: SuitePath, scenarioId: ScenarioId): PepperTestScenarioDto {
        val json = Res.readBytes("files/pepper-suites/${path.value}/${scenarioId.value}.json").decodeToString()
        println("scenario: $json")
        return Json.decodeFromString(json)
    }
}
