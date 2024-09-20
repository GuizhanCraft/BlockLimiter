import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "net.guizhanss"
version = "UNOFFICIAL"
description = "BlockLimiter"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.github.StarWishsama:Slimefun4:2024.3")
    implementation("net.guizhanss:GuizhanLib-api:1.8.1")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.compileKotlin {
    kotlinOptions {
        javaParameters = true
        jvmTarget = "17"
    }
}

tasks.shadowJar {
    fun doRelocate(from: String) {
        val last = from.split(".").last()
        relocate(from, "net.guizhanss.blocklimiter.libs.$last")
    }
    doRelocate("net.guizhanss.guizhanlib")
    doRelocate("org.bstats")
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "net.guizhanss.blocklimiter.BlockLimiter"
    apiVersion = "1.16"
    authors = listOf("ybw0014")
    description = "The rewritten version of HeadLimiter, specified for Slimefun Chinese version."
    depend = listOf("Slimefun")
    softDepend = listOf("GuizhanLibPlugin")

    commands {
        register("blocklimiter") {
            description = "BlockLimiter command"
            aliases = listOf("bl", "sfbl", "sfblocklimiter")
        }
    }

    permissions {
        register("blocklimiter.info") {
            description = "Use info command"
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("blocklimiter.list") {
            description = "Use list command"
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
        register("blocklimiter.bypass") {
            description = "Bypass the limit"
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("blocklimiter.debug") {
            description = "Use debug command"
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("blocklimiter.reload") {
            description = "Use reload command"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}
