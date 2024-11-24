package io.github.vooft.pepper.components.reportsuitesstats

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import io.github.koalaplot.core.bar.DefaultVerticalBar
import io.github.koalaplot.core.bar.StackedVerticalBarPlot
import io.github.koalaplot.core.bar.VerticalBarPlotStackedPointEntry
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.rememberIntLinearAxisModel
import io.github.vooft.pepper.components.utils.HoverSurface
import io.github.vooft.pepper.components.utils.Panel
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.http.LoadablePepperSuite
import io.github.vooft.pepper.reports.api.PepperTestStatus

@Composable
fun ReportSuitesStatsScreen(
    modifier: Modifier = Modifier,
    suites: List<LoadablePepperSuite>,
    currentSuite: LoadablePepperSuite?,
    onSuiteClicked: (LoadablePepperSuite) -> Unit
) {
    val maxScenarios = suites.maxOf { it.suite.scenarios.size }

    Panel(modifier = modifier, title = "Test suites statistics") {
        XYGraph(
            modifier = modifier,
            xAxisTitle = {}, // must be here to pick up the right overload
            xAxisModel = remember { CategoryAxisModel(List(suites.size) { it }) },
            xAxisLabels = { index ->
                val suite = suites[index]
                Text(
                    text = suites[index].suiteItem.name,
                    fontWeight = when {
                        suite == currentSuite -> FontWeight.Bold
                        else -> FontWeight.Normal
                    }
                )
            },
            yAxisModel = rememberIntLinearAxisModel(0..(maxScenarios * 1.5).toInt(), minorTickCount = 0),
            yAxisLabels = { value -> Text(value.toString()) }
        ) {
            StackedVerticalBarPlot(
                data = remember {
                    suites.mapIndexed { suiteIndex, suite ->
                        object : VerticalBarPlotStackedPointEntry<Int, Int> {
                            override val x: Int = suiteIndex
                            override val yOrigin: Int = 0
                            override val y: List<Int> = buildList {
                                var previous = 0
                                for (category in Categories.entries) {
                                    previous += suite.byCategory(category)
                                    add(previous)
                                }
                            }
                        }
                    }
                },
                bar = { xIndex, barIndex ->
                    val suite = suites[xIndex]
                    val category = Categories.fromIndex(barIndex)
                    println("xIndex: $xIndex, barIndex: $barIndex, category: $category, count: ${suite.byCategory(category)}")

                    DefaultVerticalBar(
                        brush = SolidColor(Categories.fromIndex(barIndex).color),
                        modifier = Modifier.fillMaxWidth().clickable { onSuiteClicked(suite) }
                    ) {
                        HoverSurface {
                            Text("${category.display}: ${suite.byCategory(category)}")
                        }
                    }
                },
                animationSpec = TweenSpec(durationMillis = 0)
            )
        }
    }
}

enum class Categories(val display: String, val color: Color) {
    PASSED("Passed", PepperColor.Green),
    FAILED("Failed", PepperColor.Red),
    SKIPPED("Skipped", PepperColor.Grey);

    companion object {
        fun fromIndex(index: Int) = entries.elementAtOrNull(index) ?: error("Unknown category index $index")
    }
}

private fun LoadablePepperSuite.byCategory(category: Categories) = when (category) {
    Categories.PASSED -> passedCount
    Categories.FAILED -> failedCount
    Categories.SKIPPED -> skippedCount
}

private val LoadablePepperSuite.passedCount get() = suite.scenarios.count { it.status == PepperTestStatus.PASSED }
private val LoadablePepperSuite.failedCount get() = suite.scenarios.count { it.status == PepperTestStatus.FAILED }
private val LoadablePepperSuite.skippedCount get() = suite.scenarios.count { it.status == PepperTestStatus.SKIPPED }
