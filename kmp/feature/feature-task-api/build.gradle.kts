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
            api("com.rickclephas.kmm:kmm-viewmodel-core:_")
        }

        sourceSets["androidMain"].dependencies {
            api("androidx.lifecycle:lifecycle-viewmodel-ktx:_")
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
