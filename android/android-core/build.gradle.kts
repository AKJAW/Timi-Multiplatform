
plugins {
    id("android-library-convention")
    id("compose-library-convention")
}

dependencies {
    implementation(project(":kmp:core:core-shared"))
    implementation(project(":kmp:feature:feature-task-api"))

    implementation("androidx.appcompat:appcompat:_")
    implementation("com.google.android.material:material:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.compose.ui:ui-tooling:_")
    implementation("androidx.compose.material:material-icons-extended:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
    implementation("com.soywiz.korlibs.klock:klock:_")

    implementation("io.insert-koin:koin-android:_")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")
}
