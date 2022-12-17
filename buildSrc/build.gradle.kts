plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api("com.android.tools.build:gradle:_")
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    api("de.mannodermaus.gradle.plugins:android-junit5:_")
}
