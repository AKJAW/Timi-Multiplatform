import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.akjaw.timicompose"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.akjaw.timicompose.HiltTestRunner"
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.ui)
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:stopwatch"))
    implementation(project(":features:task:list"))
    implementation(project(":features:task:detail"))
    implementation(project(":features:task:task_api"))
    implementation(project(":features:settings"))
    implementation(project(":kmp:shared"))

    implementation("io.insert-koin:koin-android:_")
    implementation("io.insert-koin:koin-androidx-compose:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("androidx.activity:activity-compose:_")

//    implementation("com.google.dagger:hilt-android:_")
//    kapt("com.google.dagger:hilt-android-compiler:_")
//    kapt("androidx.hilt:hilt-compiler:_")

    // Dagger Core
    implementation("com.google.dagger:dagger:_")
    kapt("com.google.dagger:dagger-compiler:_")

// Dagger Android
    api("com.google.dagger:dagger-android:_")
    api("com.google.dagger:dagger-android-support:_")
    kapt("com.google.dagger:dagger-android-processor:_")

// Dagger - Hilt
    implementation("com.google.dagger:hilt-android:_")
    kapt("com.google.dagger:hilt-android-compiler:_")


    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:_")
    androidTestImplementation("androidx.test.espresso:espresso-core:_")
    androidTestImplementation("com.google.dagger:hilt-android-testing:_")
    androidTestImplementation("com.squareup.sqldelight:android-driver:_")
    androidTestImplementation("com.soywiz.korlibs.klock:klock:_")
    debugImplementation("androidx.compose.ui:ui-test-manifest:_")
    kaptAndroidTest("com.google.dagger:hilt-compiler:_")
}
