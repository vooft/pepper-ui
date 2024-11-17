package io.github.vooft.pepper.components.singlescenario

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.vooft.pepper.components.utils.PassFailChip
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.components.utils.color
import io.github.vooft.pepper.reports.api.PepperScenarioStatus
import io.github.vooft.pepper.reports.api.PepperStepPrefix
import io.github.vooft.pepper.reports.api.PepperTestScenario
import io.github.vooft.pepper.reports.api.PepperTestStep
import io.github.vooft.pepper.reports.api.status

@Composable
fun SingleScenarioScreen(modifier: Modifier = Modifier, scenario: PepperTestScenario) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            PassFailChip(status = scenario.status)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = scenario.name, style = MaterialTheme.typography.h5)
        }

        Text(text = scenario.className, style = MaterialTheme.typography.caption, color = PepperColor.Grey400)

        Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), thickness = 3.dp)

        val stepsByPrefix = buildList {
            var currentPrefix = scenario.steps.first().prefix
            var currentSteps = mutableListOf<PepperTestStep>()

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
                            ScenarioStep(modifier = Modifier.fillMaxWidth(), step = step)
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScenarioStep(modifier: Modifier = Modifier, step: PepperTestStep) {
    var expanded by remember(key1 = step) { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Card(
        modifier = modifier.animateContentSize(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)),
        shape = shape,
        onClick = { expanded = !expanded }
    ) {
        Column {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (step.status) {
                        PepperScenarioStatus.PASSED -> Icons.Default.Check
                        PepperScenarioStatus.FAILED -> Icons.Default.Close
                    },
                    contentDescription = null,
                    tint = step.status.color
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(6f),
                    text = step.name,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.2f)
                        .rotate(rotationState),
                    onClick = { expanded = !expanded }
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            if (expanded) {
                PepperTestStepExpand(modifier = Modifier.fillMaxWidth().padding(8.dp), step = step)
            }
        }
    }
}

@Composable
private fun PepperTestStepExpand(modifier: Modifier = Modifier, step: PepperTestStep) {
    Column(modifier = modifier) {
        for (argument in step.arguments) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = argument.name,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = argument.value,
                    fontFamily = FontFamily.Monospace
                )

            }
        }

        val error = step.error
        if (error != null) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = error.message,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

data class PrefixedSteps(val prefix: PepperStepPrefix, val steps: List<PepperTestStep>)
