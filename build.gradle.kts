import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm") version "1.4.31"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("plugin.serialization") version "1.4.31"
}

val JVM_VERSION = JavaVersion.VERSION_11
val JVM_VERSION_STRING = JVM_VERSION.versionString

group = "de.hglabor"
version = "2.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
    //KSPIGOT AND BLUEUTILS
    implementation("net.axay", "BlueUtils", "1.0.2")
    implementation("net.axay", "kspigot","1.16.26")
    //SPIGOT
    compileOnly("org.spigotmc", "spigot-api", "1.16.5-R0.1-SNAPSHOT")
    //KMONGO
    implementation("org.litote.kmongo", "kmongo-core", "4.2.3")
    implementation("org.litote.kmongo", "kmongo-serialization-mapping", "4.2.3")
}

java.sourceCompatibility = JVM_VERSION
java.targetCompatibility = JVM_VERSION

tasks.withType<KotlinCompile> {
    configureJvmVersion()
    configureJvmVersion()
}

tasks {
    shadowJar {
        minimize()
        simpleRelocate("net.axay.kspigot")
    }
}

val JavaVersion.versionString
    get() = majorVersion.let {
        val version = it.toInt()
        if (version <= 10) "1.$it" else it
    }

fun KotlinCompile.configureJvmVersion() {
    kotlinOptions.jvmTarget = JVM_VERSION_STRING
}

fun ShadowJar.simpleRelocate(pattern: String) {
    relocate(pattern, "${project.group}.${project.name.toLowerCase()}.shadow.$pattern")
}