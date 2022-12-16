import de.fayard.refreshVersions.core.versionFor

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("TimiDatabase") {
        packageName = "com.akjaw.timi.kmp.feature.task.dependency.database"
        schemaOutputDirectory = file("src/sqldelight/")
    }
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
                implementation("com.squareup.sqldelight:runtime:_")
                implementation("com.squareup.sqldelight:coroutines-extensions:_")
                implementation("com.soywiz.korlibs.klock:klock:_")
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
                implementation("com.squareup.sqldelight:android-driver:_")
                implementation("com.squareup.sqldelight:sqlite-driver:_")
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
                implementation("com.squareup.sqldelight:native-driver:_")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_") {
                    version {
                        strictly(versionFor(KotlinX.coroutines.core))
                    }
                }
            }
        }
    }
}
