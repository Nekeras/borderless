pluginManagement {
    repositories {
        maven("https://maven.minecraftforge.net")
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "net.minecraftforge.gradle" -> useModule("net.minecraftforge.gradle:ForgeGradle:5.1.+")
            }
        }
    }
}

rootProject.name = "BorderlessWindow"
