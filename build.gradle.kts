val minecraftVersion: String by extra
val modVersion: String by extra
val forgeVersion: String by extra

plugins {
    java
    id("net.minecraftforge.gradle")
}

group = "de.nekeras"
version = "$minecraftVersion-$modVersion"

repositories {
    mavenCentral()
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
    mappings("official", minecraftVersion)

    val client by runs.creating {
        workingDirectory(project.file("run"))

        property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
        property("forge.logging.console.level", "debug")

        val borderless by mods.creating {
            source(sourceSets.main.get())
        }
    }

    val server by runs.creating {
        workingDirectory(project.file("run"))

        property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
        property("forge.logging.console.level", "debug")

        val borderless by mods.creating {
            source(sourceSets.main.get())
        }
    }
}

tasks.processResources {
    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        include("META-INF/mods.toml")
        expand("version" to project.version)
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
}
