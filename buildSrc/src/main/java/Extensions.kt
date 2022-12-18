package org.gradle.kotlin.dsl

import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinProjectExtension.iosDependencies(configure: KotlinDependencyHandler.() -> Unit) {
    sourceSets {
        findByName("iosMain")?.run {
            dependencies {
                configure()
            }
        }
    }
}
