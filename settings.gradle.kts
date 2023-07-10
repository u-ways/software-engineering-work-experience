dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val kotlinVersion = "1.8.10"
            val kotestVersion = "5.5.4"
            val junitVersion = "5.8.2"

            library("kotlin-reflect", "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            library("kotlin-stdlib", "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
            library("kotest-runner-junit5", "io.kotest:kotest-runner-junit5:$kotestVersion")
            library("kotest-assertions-core", "io.kotest:kotest-assertions-core:$kotestVersion")
            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:$junitVersion")

            plugin("jetbrains-kotlin-jvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
        }
    }
}
