import com.adarshr.gradle.testlogger.theme.ThemeType
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// CUSTOM FLAGS --------------------------------------------------------------

/**
 * Allow setting custom test directory reporting when running tasks to
 * prevent overwriting reports when running each challenge separately.
 */
val challengeNumberFlag = "challengeNumber"

/**
 * This is used for reverse testing the challenge tests to ensure certain tests
 * are not passing by accident when the challenges are not implemented.
 */
val inverseMatcherFlag = "inverseMatcher"

// CUSTOM FUNCTIONS -----------------------------------------------------------

/**
 * Convince function to run specific task configurations only if the challenge
 * number flag is set. (e.g. to separate challenge tests report, so they won't
 * be overwritten by other challenge tests report)
 *
 * This function will pass a directory name to the logic function, which is
 * the challenge number flag value. (e.g. "challenge-1")
 *
 * @param logic The logic to run if the challenge number flag is set.
 */
fun onSingleChallengeRuns(logic: (String) -> Unit) =
    System.getProperty(challengeNumberFlag, "0")
        .toInt()
        .let { if (it > 0) logic.invoke("challenge-$it") }

// META -----------------------------------------------------------------------

group = "io.github.u.ways"
version = System.getenv("VERSION") ?: "DEV-SNAPSHOT"
description = "A Practical & Relevant Work Experience Project For Students Who Are Interested In Software Development."

// IMPORTS --------------------------------------------------------------------

repositories(RepositoryHandler::mavenCentral)

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    jacoco
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.adarshr.test.logger)
}

dependencies {
    implementation(libs.kotlin.stdlib)
    testImplementation(rootProject.libs.junit.jupiter)
    testImplementation(rootProject.libs.kotest.assertions.core)
    testImplementation(rootProject.libs.kotest.runner.junit5)
}

// CONFIGS --------------------------------------------------------------------

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
    onSingleChallengeRuns { directory ->
        testResultsDir.set(testResultsDir.get().asFile.resolve(directory))
        testReportDir.set(testReportDir.get().asFile.resolve(directory))
    }
}

testlogger {
    theme = ThemeType.STANDARD
    logLevel = LogLevel.QUIET
}

tasks {
    test {
        useJUnitPlatform()
        testLogging { showStandardStreams = true }
        systemProperty(inverseMatcherFlag, System.getProperty(inverseMatcherFlag, "false"))
        finalizedBy(jacocoTestReport)
    }

    jacocoTestReport {
        reports {
            onSingleChallengeRuns { filename ->
                html.required.set(false)
                with(xml) {
                    required.set(true)
                    outputLocation.set(xml.outputLocation.get().asFile.resolve("$filename.xml"))
                }
            }
        }
    }

    wrapper {
        distributionType = ALL
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = VERSION_17.toString()
    }
}