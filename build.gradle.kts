val minecraftVersion: String by extra
val modVersion: String by extra
val forgeVersion: String by extra

plugins {
    kotlin("jvm")
    id("net.minecraftforge.gradle")
}

group = "de.nekeras"
version = "$minecraftVersion-$modVersion"

configurations {
    maybeCreate("fatJar")
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")

    "fatJar"(kotlin("stdlib"))
    "fatJar"(kotlin("reflect"))
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
tasks.processResources {
    doFirst {
        delete(File(sourceSets.main.get().output.resourcesDir, "/META-INF/mods.toml"))
    }

    from(sourceSets.main.get().resources.srcDirs) {
        include("META-INF/mods.toml")
        expand(
            "version" to project.version
        )
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
            "Specification-Title" to project.name,
            "Specification-Vendor" to "Nekeras",
            "Specification-Version" to project.version,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "Nekeras"
        )
    }

    from(configurations.named("fatJar").get().map { dependencyArchive ->
        zipTree(dependencyArchive).matching {
            exclude {
                it.relativePath.segments.getOrNull(index = 0) == "META-INF"
            }
        }
    })
}
