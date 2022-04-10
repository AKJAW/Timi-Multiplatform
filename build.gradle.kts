buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Android.tools.build.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("com.rickclephas.kmp:kmp-nativecoroutines-gradle-plugin:_")
        classpath(Square.sqlDelight.gradlePlugin)
        classpath("org.jlleitschuh.gradle:ktlint-gradle:_")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    tasks.withType<Test> {
        if (project.path.contains("kmp").not()) {
            useJUnitPlatform()
        }
        testLogging {
            events("passed", "failed", "skipped", "standardOut", "standardError")
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
