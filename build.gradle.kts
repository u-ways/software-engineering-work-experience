import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

allprojects {
    group = "io.github.u.ways"
    version = System.getenv("VERSION") ?: "DEV-SNAPSHOT"
    description = "Sky's Work Experience Project."

    repositories(RepositoryHandler::mavenCentral)

    apply<JavaLibraryPlugin>()
    apply(plugin = rootProject.libs.plugins.jetbrains.kotlin.jvm.get().pluginId)

    dependencies {
        implementation(libs.kotlin.stdlib)
        testImplementation(rootProject.libs.junit.jupiter)
        testImplementation(rootProject.libs.kotest.assertions.core)
        testImplementation(rootProject.libs.kotest.runner.junit5)
    }

    tasks {
        test {
            useJUnitPlatform()
            testLogging { showStandardStreams = true }
        }
    }

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
    }
}

tasks {
    wrapper {
        distributionType = ALL
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = VERSION_17.toString()
    }
}