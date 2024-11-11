package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestSuite
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

    override suspend fun loadSuite(path: String): PepperTestSuite {
        val json = Res.readBytes("files/pepper-suites/$path/pepper.json").decodeToString()
        println("suite: $json")
        return Json.decodeFromString(json)
    }

    override suspend fun loadScenario(path: String, scenarioId: String): PepperTestScenario {
        val json = Res.readBytes("files/pepper-suites/$path/$scenarioId.json").decodeToString()
        println("scenario: $json")
        return Json.decodeFromString(json)
    }
}
