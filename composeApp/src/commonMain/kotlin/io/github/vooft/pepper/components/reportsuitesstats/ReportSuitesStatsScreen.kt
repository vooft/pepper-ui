package io.github.vooft.pepper.components.reportsuitesstats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.koalaplot.core.bar.StackedVerticalBarPlot
import io.github.koalaplot.core.bar.solidBar
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.rememberFloatLinearAxisModel
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
            xAxisModel = remember { CategoryAxisModel(suites.map { it.suiteItem.name }) },
            yAxisModel = rememberFloatLinearAxisModel(0f..maxScenarios.toFloat(), minorTickCount = 0),
        ) {
            StackedVerticalBarPlot {
                suites.map {
                    series(solidBar(PepperColor.Green)) {
                        item(it.suiteItem.name, it.passedCount.toFloat())
                    }

                    series(solidBar(PepperColor.Red)) {
                        item(it.suiteItem.name, it.failedCount.toFloat())
                    }

                    if (it.skippedCount > 0) {
                        series(solidBar(PepperColor.Grey)) {
                            item(it.suiteItem.name, it.skippedCount.toFloat())
                        }
                    }
                }
            }
        }
    }
}

private val LoadablePepperSuite.passedCount get() = suite.scenarios.count { it.status == PepperTestStatus.PASSED }
private val LoadablePepperSuite.failedCount get() = suite.scenarios.count { it.status == PepperTestStatus.FAILED }
private val LoadablePepperSuite.skippedCount get() = suite.scenarios.count { it.status == PepperTestStatus.SKIPPED }
