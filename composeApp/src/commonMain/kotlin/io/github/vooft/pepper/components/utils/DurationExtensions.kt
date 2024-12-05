package io.github.vooft.pepper.components.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit.MICROSECONDS
import kotlin.time.DurationUnit.MILLISECONDS
import kotlin.time.DurationUnit.NANOSECONDS
import kotlin.time.DurationUnit.SECONDS

fun Duration.toReadableString() = when {
    this.inWholeMinutes > 0 -> toString()
    this.inWholeSeconds > 0 -> toString(SECONDS, 2)
    this.inWholeMilliseconds > 0 -> toString(MILLISECONDS, 2)
    this.inWholeMicroseconds > 0 -> toString(MICROSECONDS, 2)
    else -> toString(NANOSECONDS, 2)
}
