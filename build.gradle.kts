import net.minecraftforge.gradle.common.util.MinecraftExtension
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val minecraftVersion: String by extra
val modVersion: String by extra
val forgeVersion: String by extra
val forgeMappings: String by extra

buildscript {
    repositories {
        jcenter()
        maven("https://files.minecraftforge.net/maven")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:3.+")
    }
}

plugins {
    val kotlinVersion: String by System.getProperties()
    val ktlintVersion: String by System.getProperties()

    kotlin("jvm") version kotlinVersion
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
}

apply(plugin = "net.minecraftforge.gradle")

group = "de.nekeras"
version = "${minecraftVersion}-${modVersion}"

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    create(
        group = "net.minecraftforge",
        name = "forge",
        version = "${minecraftVersion}-${forgeVersion}"
    ).apply { add("minecraft", this) }
}

configure<MinecraftExtension> {
    mappings("snapshot", forgeMappings)

    runs {
        maybeCreate("client").apply {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")

            mods {
                maybeCreate("borderless").apply {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to project.name,
                "Specification-Vendor" to "Nekeras",
                "Specification-Version" to project.version,
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "Nekeras",
                "Implementation-Timestamp" to DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.now())
            )
        )
    }
}

val forceModsTomlRefresh = task<Delete>("forceModsTomlRefresh") {
    delete(File(sourceSets.main.get().output.resourcesDir, "/META-INF/mods.toml"))
}

tasks.processResources {
    dependsOn(forceModsTomlRefresh)

    from(sourceSets.main.get().resources.srcDirs) {
        include("META-INF/mods.toml")
        expand(
            mapOf(
                "version" to project.version,
                "forge_version_major" to forgeVersion.split(".")[0]
            )
        )
    }
}
