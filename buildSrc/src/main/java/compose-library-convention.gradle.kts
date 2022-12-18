import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
}
