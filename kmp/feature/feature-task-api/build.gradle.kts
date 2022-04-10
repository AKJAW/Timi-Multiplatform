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
                implementation("io.insert-koin:koin-core:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
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
}