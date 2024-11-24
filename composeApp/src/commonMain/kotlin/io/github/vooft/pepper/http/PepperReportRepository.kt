package io.github.vooft.pepper.http

import io.github.vooft.pepper.http.PepperRoot.SuitePath
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestSuiteDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

interface PepperReportRepository {
    suspend fun loadRoot(): PepperRoot

    suspend fun loadSuite(path: SuitePath): PepperTestSuiteDto

    suspend fun loadScenario(path: SuitePath, scenarioId: String): PepperTestScenarioDto
}

suspend fun PepperReportRepository.loadSuites(paths: Collection<SuitePath>): Map<SuitePath, PepperTestSuiteDto> {
    val semaphore = Semaphore(SUITE_LOAD_PARALLELISM)
    return coroutineScope {
        paths.map {
            async {
                semaphore.withPermit {
                    it to loadSuite(it)
                }
            }
        }
            .awaitAll()
            .toMap()
    }
}

@Serializable
data class PepperRoot(
    val suites: List<PepperSuiteItem>
) {
    @Serializable
    data class PepperSuiteItem(
        val name: String,
        val path: SuitePath
    )

    @Serializable
    @JvmInline
    value class SuitePath(val value: String)
}

data class LoadablePepperSuite(
    val suiteItem: PepperRoot.PepperSuiteItem,
    val suite: PepperTestSuiteDto
)

private const val SUITE_LOAD_PARALLELISM = 4
