import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.7.10"
    id("io.papermc.paperweight.userdev") version "1.3.9-SNAPSHOT"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val mcVersion = "1.19.2"
val cloudNetVersion = "4.0.0-SNAPSHOT"

group = "de.hglabor"
version = "${mcVersion}_v1"
description = "bingo für hglabor.de"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    //@Suppress("DEPRECATION")
    //jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    //PAPERWEIGHT
    paperDevBundle("${mcVersion}-R0.1-SNAPSHOT")
    //KSPIGOT AND HGLABORUTILS
    //implementation("net.axay", "BlueUtils", "1.0.9")
    implementation("de.hglabor", "hglabor-utils", "1.19.2_v3")
    implementation("net.axay", "kspigot","1.19.0")
    //KMONGO
    implementation("org.litote.kmongo", "kmongo-core", "4.7.0")
    implementation("org.litote.kmongo", "kmongo-serialization-mapping", "4.7.0")
    //CLOUDNET
    compileOnly("eu.cloudnetservice.cloudnet:bridge:$cloudNetVersion")
}

tasks {
    build {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
    shadowJar {
        fun reloc(pkg: String) = relocate(pkg, "de.hglabor.bingo.dependency.$pkg")
        reloc("net.axay.kspigot")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

bukkit {
    main = "de.hglabor.Bingo"
    apiVersion = "1.19"
    depend = listOf("Chunky")
    authors = listOf("mooziii", "polylymer", "copyandexecute", "soho5k")
    /* conflicts with brigardier
    commands {
        register("bingo") { description = "See which Items you have to find."; aliases = listOf("b") }
        register("start") { description = "Start the Game." }
        register("settings") { description = "Edit the Bingo Settings." }
        register("top") { description = "Teleport you to the highest y coordinate at your location." }
        register("teams") { description = "Choose your team!" }
        register("backpack") { description = "Open your team-backpack!"; aliases = listOf("bp") }
        register("teamchat") { description = "Communicate with your team members!"; aliases = listOf("tc") }
    }
     */
}
