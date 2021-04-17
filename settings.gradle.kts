pluginManagement {
    val kotlinVersion: String by settings
    val ktlintVersion: String by settings
    val forgeGradleVersion: String by settings

    repositories {
        gradlePluginPortal()
        maven("https://files.minecraftforge.net/maven")
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "net.minecraftforge.gradle" -> useModule("net.minecraftforge.gradle:ForgeGradle:$forgeGradleVersion")
            }
        }
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    }
}

rootProject.name = "borderless"
