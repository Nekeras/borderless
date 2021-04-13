import net.minecraftforge.gradle.common.util.MinecraftExtension
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val minecraftVersion: String by extra
val modVersion: String by extra
val forgeVersion: String by extra

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
    java
    `maven-publish`
}

apply(plugin = "net.minecraftforge.gradle")

val mainSourceSet: SourceSet = java.sourceSets.findByName("main")!!

fun DependencyHandlerScope.minecraft(): ExternalModuleDependency =
    create(
        group = "net.minecraftforge",
        name = "forge",
        version = "${minecraftVersion}-${forgeVersion}"
    ).apply { add("minecraft", this) }

group = "de.nekeras"
version = "${minecraftVersion}-${modVersion}"

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft()
}

configure<MinecraftExtension> {
    mappings("official", minecraftVersion)

    runs {
        "client" {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")

            mods {
                "borderless" {
                    source(mainSourceSet)
                }
            }
        }
    }
}

tasks.withType<Jar> {
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

val forceModsTomlRefresh = tasks.register("forceModsTomlRefresh", Delete::class.java) {
    delete(File(mainSourceSet.output.resourcesDir, "/META-INF/mods.toml"))
}.get()

tasks.withType<ProcessResources> {
    dependsOn(forceModsTomlRefresh)

    from(mainSourceSet.resources.srcDirs) {
        include("META-INF/mods.toml")
        expand(
            mapOf(
                "version" to project.version,
                "forge_version_major" to forgeVersion.split(".")[0]
            )
        )
    }
}

tasks.withType<Javadoc> {
    source(mainSourceSet.allJava)
}

val sourcesJar = tasks.register("sourcesJar", Jar::class.java) {
    classifier = "sources"
    from(mainSourceSet.allJava)
}.get()

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    classifier = "javadoc"
    from(tasks.getByName("javadoc"))
}.get()

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourcesJar)
            artifact(javadocJar)
        }
    }
}

