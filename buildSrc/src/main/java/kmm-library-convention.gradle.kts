import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests

// https://discuss.gradle.org/t/plugins-and-apply-from-in-the-kotlin-dsl/28662/8
// https://github.com/gradle/kotlin-dsl-samples/issues/1287
// https://github.com/gradle/gradle/issues/11090
// https://proandroiddev.com/sharing-build-logic-with-kotlin-dsl-203274f73013
// https://quickbirdstudios.com/blog/gradle-kotlin-buildsrc-plugin-android/

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

kotlin.targets.withType(KotlinNativeTarget::class.java) {
    binaries.all {
        freeCompilerArgs += "-Xruntime-logs=gc=info"
    }
}

kotlin {
    android()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets.matching { it.name.endsWith("Test") }
        .configureEach {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
}

project.extensions.findByType(org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension::class.java)?.apply {
    if (project.findProperty("ios") == "true") {
        ios()
        // Note: iosSimulatorArm64 target requires that all dependencies have M1 support
        iosSimulatorArm64()
        sourceSets["iosSimulatorArm64Main"].dependsOn(sourceSets["iosMain"])
        sourceSets["iosSimulatorArm64Test"].dependsOn(sourceSets["iosTest"])

        targets.withType(KotlinNativeTargetWithSimulatorTests::class.java) {
            testRuns["test"].deviceId = "iPhone 14"
        }
    }
}
