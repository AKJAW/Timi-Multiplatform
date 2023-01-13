import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.akjaw.timicompose"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
    lint {
        warningsAsErrors = true
        abortOnError = false
    }
}

dependencies {
    implementation(project(":android:android-core"))
    implementation(project(":android:feature:feature-stopwatch-ui"))
    implementation(project(":android:feature:feature-task-ui"))
    implementation(project(":android:feature:feature-settings-ui"))
    implementation(project(":kmp:shared"))
    implementation(project(":kmp:core:core-shared"))
    implementation(project(":kmp:feature:feature-task-dependency"))

    implementation("io.insert-koin:koin-android:_")
    implementation("io.insert-koin:koin-androidx-compose:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("androidx.activity:activity-compose:_")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testImplementation(project(":kmp:core:core-test"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:_")
    androidTestImplementation(project(":kmp:core:core-test"))
    androidTestImplementation("androidx.test.espresso:espresso-core:_")
    androidTestImplementation("com.squareup.sqldelight:android-driver:_")
    androidTestImplementation("com.soywiz.korlibs.klock:klock:_")
    androidTestImplementation("com.adevinta.android:barista:_")
    androidTestImplementation("io.insert-koin:koin-test:_")
    debugImplementation("androidx.compose.ui:ui-test-manifest:_")
}
