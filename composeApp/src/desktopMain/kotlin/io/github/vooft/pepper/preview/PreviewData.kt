package io.github.vooft.pepper.preview

import io.github.vooft.pepper.reports.api.PepperScenarioStatus
import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestStep
import kotlinx.atomicfu.atomic
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

object PreviewData {
    private val counter = atomic(0)

    fun aPepperTestScenario() = PepperTestScenario(
        id = "${counter.incrementAndGet()}",
        name = "Test scenario ${counter.incrementAndGet()}",
        className = "io.github.vooft.pepper.TestScenario",
        version = 1,
        status = PepperScenarioStatus.PASSED,
        startedAt = Clock.System.now().minus(20.seconds),
        finishedAt = Clock.System.now(),
        steps = listOf(
            PepperTestStep(
                id = "${counter.incrementAndGet()}",
                name = "Step ${counter.incrementAndGet()}",
                result = "bla ${counter.incrementAndGet()}",
                error = null,
                startedAt = Clock.System.now().minus(15.seconds),
                finishedAt = Clock.System.now(),
                arguments = listOf(aPepperTestStepArgument())
            ),
        )
    )

    fun aPepperTestStepArgument() = PepperTestStep.StepArgument(
        name = "arg${counter.incrementAndGet()}",
        type = "kotlin.String",
        value = "value${counter.incrementAndGet()}"
    )
}