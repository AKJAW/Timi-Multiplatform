import de.fayard.refreshVersions.core.versionFor

plugins {
    id("kmm-library-convention")
    id("com.rickclephas.kmp.nativecoroutines")
}

version = "1.0"

kotlin {

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation("io.insert-koin:koin-core:_")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
            implementation("com.soywiz.korlibs.klock:klock:_")
        }

        sourceSets["commonTest"].dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test-common:_")
            implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:_")
            implementation("app.cash.turbine:turbine:_")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
            implementation("io.kotest:kotest-assertions-core:_")
        }

        sourceSets["androidTest"].dependencies {
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
}
