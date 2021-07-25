pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net")
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "net.minecraftforge.gradle" -> useModule("net.minecraftforge.gradle:ForgeGradle:5.1.+")
            }
        }
    }
    plugins {
        kotlin("jvm") version "1.5.20"
        id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
    }
}

rootProject.name = "BorderlessWindow"
