import de.fayard.refreshVersions.core.versionFor

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.rickclephas.kmp.nativecoroutines")
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
                implementation(project(":kmp:core:core-shared"))
                implementation(project(":kmp:feature:feature-task-api"))

                implementation("io.insert-koin:koin-core:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
                implementation("com.soywiz.korlibs.klock:klock:_")
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
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
}
