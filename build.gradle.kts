import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)

}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlinx" && requested.name == "atomicfu") {
                useVersion("0.26.0")
            }
        }
    }

    detekt {
        buildUponDefaultConfig = true
        config.from(files("$rootDir/detekt.yaml"))
        basePath = rootDir.absolutePath
    }

    tasks.withType<LintTask> {
        source("build.gradle.kts", "settings.gradle.kts")
        exclude {
            it.file.path.startsWith("$buildDir") && !it.file.path.endsWith("gradle.kts")
        }
        dependsOn("formatKotlin")
    }

    tasks.withType<FormatTask> {
        source("build.gradle.kts", "settings.gradle.kts")
        exclude {
            it.file.path.startsWith("$buildDir") && !it.file.path.endsWith("gradle.kts")
        }
    }
}
