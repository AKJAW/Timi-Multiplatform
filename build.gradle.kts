
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Android.tools.build.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("com.google.dagger:hilt-android-gradle-plugin:_")
        classpath("com.rickclephas.kmp:kmp-nativecoroutines-gradle-plugin:_")
        classpath(Square.sqlDelight.gradlePlugin)
        classpath("org.jlleitschuh.gradle:ktlint-gradle:_")
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "failed", "skipped", "standardOut", "standardError")
        }
    }
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}