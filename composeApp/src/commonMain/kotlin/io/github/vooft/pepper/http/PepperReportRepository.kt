package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestSuiteDto
import kotlinx.serialization.Serializable

interface PepperReportRepository {
    suspend fun loadRoot(): PepperRoot

    suspend fun loadSuite(path: String): PepperTestSuiteDto

    suspend fun loadScenario(path: String, scenarioId: String): PepperTestScenarioDto
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
