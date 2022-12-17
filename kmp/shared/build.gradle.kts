import de.fayard.refreshVersions.core.versionFor
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.squareup.sqldelight")
}

android {
    compileSdk = 30
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }

//    lint {
//        isWarningsAsErrors = true
//        isAbortOnError = true
//    }
}

version = "1.0"

kotlin {
    android()
    ios()
    iosSimulatorArm64()
    sourceSets["iosSimulatorArm64Main"].dependsOn(sourceSets["iosMain"])
    sourceSets["iosSimulatorArm64Test"].dependsOn(sourceSets["iosTest"])

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests::class.java) {
        testRuns["test"].deviceId = "iPhone 14"
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kmp:core:core-shared"))
                api(project(":kmp:feature:feature-stopwatch"))
                api(project(":kmp:feature:feature-task-api"))
                implementation(project(":kmp:feature:feature-task-dependency"))
                implementation("io.insert-koin:koin-core:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
                implementation("co.touchlab:stately-common:_")
                api("co.touchlab:kermit:_")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:_")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
                implementation("app.cash.turbine:turbine:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
                implementation("io.kotest:kotest-assertions-core:_")
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:_")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:_")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_") {
                    version {
                        strictly(versionFor(KotlinX.coroutines.core))
                    }
                }
            }
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
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
