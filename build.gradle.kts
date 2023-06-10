plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "dev.mrqrdt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    implementation("com.auth0:java-jwt:4.4.0")
}

bukkit {
    main = "dev.mrqrdt.itemchat.ItemChat"
    apiVersion = "1.13"
    author = "Arventis"

    commands {
        register("itemchat") {
            description = "View a Player's Inventory"
            usage = "Usage: /<command> [token]"
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        dependsOn(shadowJar)
        enabled = false
    }

    shadowJar {
        archiveClassifier.set("")
        minimize()
    }

    test {
        useJUnitPlatform()
    }
}

kotlin {
    jvmToolchain(17)
}
