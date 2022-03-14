plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

android {
    compileSdk = 30
    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }
//
//    lint {
//        isWarningsAsErrors = true
//        isAbortOnError = true
//    }
}

version = "1.0"

kotlin {
    android()
    ios()

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
//                optIn("kotlin.RequiresOptIn")
//                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-core:3.1.5")
//                implementation(libs.coroutines.core)
//                implementation(libs.touchlab.stately)
//                api(libs.touchlab.kermit)
            }
        }
        val commonTest by getting {
            dependencies {
//                implementation(libs.bundles.shared.commonTest)
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
//                implementation(libs.bundles.shared.androidTest)
            }
        }
        val iosMain by getting {
            dependencies {
//                implementation(libs.sqlDelight.native)
//                implementation(libs.ktor.client.ios)
//                val coroutineCore = libs.coroutines.core.get()
//                implementation("${coroutineCore.module.group}:${coroutineCore.module.name}:${coroutineCore.versionConstraint.displayName}") {
//                    version {
//                        strictly(libs.versions.coroutines.get())
//                    }
//                }
            }
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
//            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

    cocoapods {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPKit"
        framework {
            isStatic = false // SwiftUI preview requires dynamic framework
        }
        ios.deploymentTarget = "15.0"
        podfile = project.file("../../ios/Podfile")
    }
}
