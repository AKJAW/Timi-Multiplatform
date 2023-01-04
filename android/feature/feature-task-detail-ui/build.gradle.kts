
plugins {
    id("android-library-convention")
    id("compose-library-convention")
}

dependencies {
    implementation(project(":android:android-core"))
    implementation(project(":kmp:core:core-shared"))
    implementation(project(":kmp:feature:feature-task-api"))
    implementation(project(":kmp:feature:feature-task-dependency")) // TODO remove later

    implementation("androidx.core:core-ktx:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("com.google.android.material:material:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.compose.ui:ui-tooling:_")
    implementation("androidx.compose.material:material-icons-extended:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:_")
    implementation("com.google.accompanist:accompanist-pager:_")
    implementation("com.soywiz.korlibs.klock:klock:_")

    implementation("io.insert-koin:koin-android:_")
    implementation("io.insert-koin:koin-androidx-compose:_")

    testImplementation(project(":kmp:core:core-test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testImplementation("org.junit.jupiter:junit-jupiter-params:_")
    testImplementation("io.kotest:kotest-assertions-core:_")
    testImplementation("io.mockk:mockk:_")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("app.cash.turbine:turbine:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    androidTestImplementation(project(":kmp:core:core-test"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:_")
    debugImplementation("androidx.compose.ui:ui-test-manifest:_")
    androidTestImplementation("androidx.test.espresso:espresso-core:_")
}
