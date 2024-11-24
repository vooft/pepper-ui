package io.github.vooft.pepper.components.reportsuitesstats

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import io.github.koalaplot.core.bar.DefaultVerticalBar
import io.github.koalaplot.core.bar.StackedVerticalBarPlot
import io.github.koalaplot.core.bar.VerticalBarPlotStackedPointEntry
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.IntLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.vooft.pepper.components.utils.HoverSurface
import io.github.vooft.pepper.components.utils.Panel
import io.github.vooft.pepper.components.utils.PepperColor
import io.github.vooft.pepper.http.LoadablePepperSuite
import io.github.vooft.pepper.reports.api.PepperTestStatus

@OptIn(ExperimentalKoalaPlotApi::class)
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
            xAxisModel = CategoryAxisModel(suites.map { it.suiteItem.name }),
            yAxisModel = IntLinearAxisModel(0..(maxScenarios * 1.5).toInt(), minorTickCount = 0),
        ) {
            StackedVerticalBarPlot(
                data = suites.map { suite ->
                    object : VerticalBarPlotStackedPointEntry<String, Int> {
                        override val x: String = suite.suiteItem.name
                        override val yOrigin: Int = 0
                        override val y: List<Int> = buildList {
                            var previous = 0
                            for (category in Categories.entries) {
                                previous += suite.byCategory(category)
                                add(previous)
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HoverSurface {
                            Text("${category.display}: ${suite.byCategory(category)}")
                        }
                    }
                }
            )
//            StackedVerticalBarPlot {
//                suites.map {
//                    series(solidBar(PepperColor.Green)) {
//                        item(it.suiteItem.name, it.passedCount.toFloat())
//                    }
//
//                    series(solidBar(PepperColor.Red)) {
//                        item(it.suiteItem.name, it.failedCount.toFloat())
//                    }
//
//                    if (it.skippedCount > 0) {
//                        series(solidBar(PepperColor.Grey)) {
//                            item(it.suiteItem.name, it.skippedCount.toFloat())
//                        }
//                    }
//                }
//            }
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
