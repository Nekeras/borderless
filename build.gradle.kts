val minecraftVersion: String by extra
val modVersion: String by extra
val forgeVersion: String by extra

plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
    id("net.minecraftforge.gradle")
}

group = "de.nekeras"
version = "$minecraftVersion-$modVersion"

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}

dependencies {
    create(
        group = "net.minecraftforge",
        name = "forge",
        version = "$minecraftVersion-$forgeVersion"
    ).apply { add("minecraft", this) }

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}

minecraft {
    mappings("official", minecraftVersion)

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
                "Implementation-Vendor" to "Nekeras"
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
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        include("META-INF/mods.toml")
        expand(
            mapOf(
                "version" to project.version
            )
        )
    }
}
