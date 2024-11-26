package io.github.vooft.pepper.preview

import io.github.vooft.pepper.reports.api.PepperStepPrefix
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestStatus
import io.github.vooft.pepper.reports.api.PepperTestStepDto
import kotlinx.atomicfu.atomic
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

object PreviewData {
    private val counter = atomic(0)

    fun aPepperTestScenario() = PepperTestScenarioDto(
        id = PepperTestScenarioDto.ScenarioId(counter.incrementAndGet().toString()),
        name = "Test scenario ${counter.incrementAndGet()}",
        className = "io.github.vooft.pepper.TestScenario",
        version = 1,
        startedAt = Clock.System.now().minus(20.seconds),
        finishedAt = Clock.System.now(),
        steps = listOf(
            PepperTestStepDto(
                id = PepperTestStepDto.StepId(counter.incrementAndGet().toString()),
                index = 0,
                prefix = PepperStepPrefix.GIVEN,
                status = PepperTestStatus.PASSED,
                name = "Step ${counter.incrementAndGet()}",
                result = "bla ${counter.incrementAndGet()}",
                error = null,
                startedAt = Clock.System.now().minus(15.seconds),
                finishedAt = Clock.System.now(),
                arguments = listOf(aPepperTestStepArgument())
            ),
        )
    )

    fun aPepperTestStepArgument() = PepperTestStepDto.StepArgumentDto(
        name = "arg${counter.incrementAndGet()}",
        type = "kotlin.String",
        value = "value${counter.incrementAndGet()}"
    )
}
