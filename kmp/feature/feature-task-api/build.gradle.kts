import de.fayard.refreshVersions.core.versionFor

plugins {
    id("kmm-library-convention")
    id("com.rickclephas.kmp.nativecoroutines")
}

kotlin {

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation("io.insert-koin:koin-core:_")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
        }

        sourceSets["iosMain"].dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_") {
                version {
                    strictly(versionFor(KotlinX.coroutines.core))
                }
            }
        }
    }
}
