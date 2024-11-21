package io.github.vooft.pepper.http

import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestStepDto
import kotlin.time.Duration

val PepperTestScenarioDto.duration: Duration get() = finishedAt - startedAt

val PepperTestStepDto.duration: Duration get() = finishedAt - startedAt
