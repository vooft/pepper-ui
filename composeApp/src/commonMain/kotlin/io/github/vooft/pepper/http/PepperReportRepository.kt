package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestSuite
import kotlinx.serialization.Serializable

interface PepperReportRepository {
    suspend fun loadRoot(): PepperRoot

    suspend fun loadSuite(path: String): PepperTestSuite

    suspend fun loadScenario(path: String, scenarioId: String): PepperTestScenario
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
