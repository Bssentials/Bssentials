import net.fabricmc.loom.task.RemapJarTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
  maven("https://repo1.maven.org/maven2/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://oss.sonatype.org/content/repositories/central")
  maven("https://repo.codemc.io/repository/maven-releases/")
}

val extraLibs by configurations.creating

plugins {
    id ("fabric-loom") version "0.7-SNAPSHOT"
    id ("maven-publish")
	id ("java-library")
    id ("com.github.johnrengelman.shadow") version "7.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "Bssentials"
    version = "Fabric"
    group = "com.javazilla.mods"
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
            srcDir("${rootProject.projectDir}/Bssentials-Common/src/main/java")
        }
    }
}

dependencies {
    minecraft ("com.mojang:minecraft:1.16.5")
    mappings ("net.fabricmc:yarn:1.16.5+build.9:v2")
    modImplementation ("net.fabricmc:fabric-loader:0.11.3")

    modImplementation("org.yaml:snakeyaml:1.26")
    modImplementation("com.javazilla.mods:permissions:1.2")
    extraLibs("org.yaml:snakeyaml:1.26")
}

tasks.withType<ShadowJar> {
  dependencies {
    include(dependency("org.yaml:snakeyaml:1.26"))
  }
  classifier = null
  exclude("mappings/mappings.tiny")
}


tasks.getByName("build") {
    dependsOn("shadowJar")
}