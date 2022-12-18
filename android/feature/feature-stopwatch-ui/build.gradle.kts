
plugins {
    id("android-library-convention")
    id("compose-library-convention")
}

dependencies {
    implementation(project(":android:android-core"))
    implementation(project(":kmp:core:core-shared"))
    implementation(project(":kmp:feature:feature-stopwatch"))
    implementation(project(":kmp:feature:feature-task-api"))

    implementation("androidx.core:core-ktx:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("com.google.android.material:material:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.compose.ui:ui-tooling:_")
    implementation("androidx.compose.material:material-icons-extended:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")

    implementation("io.insert-koin:koin-android:_")
    implementation("io.insert-koin:koin-androidx-compose:_")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testImplementation("org.junit.jupiter:junit-jupiter-params:_")
    testImplementation("io.strikt:strikt-core:_")
    testImplementation("io.mockk:mockk:_")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")
}
