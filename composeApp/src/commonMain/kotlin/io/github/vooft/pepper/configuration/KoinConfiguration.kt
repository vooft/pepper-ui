package io.github.vooft.pepper.configuration

import io.github.vooft.pepper.http.PepperReportClient
import io.github.vooft.pepper.suite.PepperSuiteViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun KoinApplication.koinConfiguration() {
    modules(
        module {
            single {
                HttpClient {
                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                println(message)
                            }

                        }
                        level = LogLevel.ALL
                    }

                    install(ContentNegotiation) {
                        json()
                    }
                }
            }
            single { PepperReportClient(baseUrl = "https://vooft.github.io/pepper-bdd", client = get()) }
        },

        module {
            viewModel { PepperSuiteViewModel(get()) }
        }
    )
}
