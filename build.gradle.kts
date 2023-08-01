import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// CUSTOM FLAGS --------------------------------------------------------------

/**
 * Allow setting custom test results and reporting directories to
 * separate the challenge tests from the unit tests.
 */
val challengeNumberFlag = "challengeNumber"
/**
 * This is used for reverse testing the challenge tests to ensure certain tests
 * are not passing by accident when the challenges are not implemented.
 */
val inverseMatcherFlag = "inverseMatcher"

// META -----------------------------------------------------------------------

group = "io.github.u.ways"
version = System.getenv("VERSION") ?: "DEV-SNAPSHOT"
description = "A Practical & Relevant Work Experience Project For Students Who Are Interested In Software Development."

// IMPORTS --------------------------------------------------------------------

repositories(RepositoryHandler::mavenCentral)

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.jetbrains.kotlin.jvm)
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
    System.getProperty(challengeNumberFlag, "0").toInt().let {
        if (it > 0) {
            val challengeDir = "challenge-$it"
            testResultsDir.set(testResultsDir.get().asFile.resolve(challengeDir))
            testReportDir.set(testReportDir.get().asFile.resolve(challengeDir))
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging { showStandardStreams = true }
        systemProperty(inverseMatcherFlag, System.getProperty(inverseMatcherFlag, "false"))
    }

    wrapper {
        distributionType = ALL
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = VERSION_17.toString()
    }
}