package io.github.vooft.pepper.components.singlescenario

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.components.utils.PassFailChip
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.components.utils.color
import io.github.vooft.pepper.http.duration
import io.github.vooft.pepper.reports.api.PepperStepPrefix
import io.github.vooft.pepper.reports.api.PepperTestScenarioDto
import io.github.vooft.pepper.reports.api.PepperTestStatus
import io.github.vooft.pepper.reports.api.PepperTestStepDto
import io.github.vooft.pepper.reports.api.status

@Composable
fun SingleScenarioScreen(modifier: Modifier = Modifier, scenario: PepperTestScenarioDto) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            PassFailChip(status = scenario.status)

            Spacer(modifier = Modifier.width(8.dp))

            SelectionContainer {
                Text(text = scenario.name, style = MaterialTheme.typography.button)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = scenario.duration.toString(), style = MaterialTheme.typography.caption, color = PepperColor.Grey400)
            }
        }

        SelectionContainer {
            Text(text = scenario.className, style = MaterialTheme.typography.caption, color = PepperColor.Grey400)
        }

        Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), thickness = 3.dp)

        val stepsByPrefix = buildList {
            var currentPrefix = scenario.steps.first().prefix
            var currentSteps = mutableListOf<PepperTestStepDto>()

            for (step in scenario.steps) {
                if (step.prefix != currentPrefix) {
                    add(PrefixedSteps(prefix = currentPrefix, steps = currentSteps.toList()))
                    currentPrefix = step.prefix
                    currentSteps = mutableListOf()
                }

                currentSteps.add(step)
            }

            add(PrefixedSteps(prefix = currentPrefix, steps = currentSteps))
        }

        for ((prefix, steps) in stepsByPrefix) {
            Card(modifier = Modifier.padding(vertical = 4.dp), backgroundColor = PepperColor.Grey800) {
                Column {
                    Text(
                        text = prefix.name,
                        style = MaterialTheme.typography.h6,
                        color = PepperColor.Grey400,
                        modifier = Modifier.padding(8.dp)
                    )

                    Column(modifier = Modifier.padding(8.dp)) {
                        for (step in steps) {
                            ScenarioStep(modifier = Modifier.fillMaxWidth().background(Color.White), step = step)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScenarioStep(modifier: Modifier = Modifier, step: PepperTestStepDto) {
    var expanded by remember(key1 = step) { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Column(
        modifier = modifier.animateContentSize(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp).clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (step.status) {
                    PepperTestStatus.PASSED -> Icons.Default.Check
                    PepperTestStatus.FAILED -> Icons.Default.Close
                    PepperTestStatus.SKIPPED -> Icons.Default.Cancel
                },
                contentDescription = null,
                tint = step.status.color
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = step.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (step.status == PepperTestStatus.PASSED) {
                    Text(
                        text = step.duration.toString(),
                        style = MaterialTheme.typography.caption,
                        color = PepperColor.Grey400
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                IconButton(
                    modifier = Modifier.alpha(0.2f).rotate(rotationState),
                    onClick = { expanded = !expanded }
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        }

        if (expanded) {
            PepperTestStepExpand(modifier = Modifier.fillMaxWidth().padding(8.dp), step = step)
        }
    }
}

@Composable
private fun PepperTestStepExpand(modifier: Modifier = Modifier, step: PepperTestStepDto) {
    Column(modifier = modifier) {
        if (step.arguments.isNotEmpty()) {
            Column(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                Text("Arguments:", style = MaterialTheme.typography.h6)

                for (argument in step.arguments) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        SelectionContainer {
                            Text(
                                text = argument.name,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        SelectionContainer {
                            Text(
                                text = argument.value,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

        }

        val error = step.error
        if (error != null) {
            Column(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                Text("Error:", style = MaterialTheme.typography.h6)

                var stacktraceExpanded by remember { mutableStateOf(false) }
                Text(
                    modifier = Modifier.padding(8.dp).clickable { stacktraceExpanded = !stacktraceExpanded },
                    text = error.message,
                    color = PepperColor.Red500,
                    fontFamily = FontFamily.Monospace
                )

                if (stacktraceExpanded) {
                    SelectionContainer {
                        Text(
                            modifier = Modifier.background(PepperColor.Grey300).padding(8.dp),
                            text = error.stacktrace,
                            fontFamily = FontFamily.Monospace,
                        )
                    }
                }
            }
        }
    }
}

data class PrefixedSteps(val prefix: PepperStepPrefix, val steps: List<PepperTestStepDto>)
