import de.fayard.refreshVersions.core.versionFor
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    id("kmm-library-convention")
    kotlin("native.cocoapods")
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.squareup.sqldelight")
}

version = "1.0"

kotlin {

    sourceSets {
        sourceSets["commonMain"].dependencies {
            api(project(":kmp:core:core-shared"))
            api(project(":kmp:feature:feature-stopwatch"))
            api(project(":kmp:feature:feature-task-api"))
            implementation(project(":kmp:feature:feature-task-dependency"))
            implementation("io.insert-koin:koin-core:_")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
            implementation("co.touchlab:stately-common:_")
            api("co.touchlab:kermit:_")
        }

        sourceSets["commonTest"].dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test-common:_")
            implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
            implementation("app.cash.turbine:turbine:_")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
            implementation("io.kotest:kotest-assertions-core:_")
        }

        sourceSets["commonTest"].dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test:_")
            implementation("org.jetbrains.kotlin:kotlin-test-junit:_")
        }

        iosDependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_") {
                version {
                    strictly(versionFor(KotlinX.coroutines.core))
                }
            }
        }
    }

    cocoapods {
        summary = "The shared entry point module for Timi"
        homepage = "https://github.com/AKJAW/Timi-Compose"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
            export(project(":kmp:core:core-shared"))
            export(project(":kmp:feature:feature-stopwatch"))
            export(project(":kmp:feature:feature-task-api"))
        }
        ios.deploymentTarget = "15.0"
        podfile = project.file("../../ios/Podfile")
        xcodeConfigurationToNativeBuildType["Debug"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["Beta"] = NativeBuildType.RELEASE
        xcodeConfigurationToNativeBuildType["Release"] = NativeBuildType.RELEASE
    }
}
