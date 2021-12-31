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
    id ("fabric-loom") version "0.10-SNAPSHOT"
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
    //minecraft("com.mojang:minecraft:1.17.1")
    //mappings("net.fabricmc:yarn:1.17.1+build.63:v2")
    //modImplementation("net.fabricmc:fabric-loader:0.12.3")
    
    minecraft("com.mojang:minecraft:1.18.1") 
    mappings("net.fabricmc:yarn:1.18.1+build.12:v2")
    modImplementation("net.fabricmc:fabric-loader:0.12.12")

    //Fabric api
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.45.0+1.18")

    modImplementation("org.yaml:snakeyaml:1.26")
    modImplementation("com.javazilla.mods:permissions:1.2")
   // modInclude(modImplementation("org.yaml:snakeyaml:1.26"))
   
   //include(modImplementation("cloud.commandframework", "cloud-fabric", "1.6.0"))
   //include(modImplementation("cloud.commandframework", "cloud-annotations", "1.6.0"))
}

tasks.withType<ShadowJar> {
  dependencies {
    include(dependency("org.yaml:snakeyaml:1.26"))
  }
  relocate("org.yaml", "bssentials.include")
  //relocate("cloud", "bssentials.include")
  classifier = null
  exclude("mappings/mappings.tiny")
  archiveFileName.set("Bssentials-Fabric-dev.jar")
}


tasks.getByName("build") {
   dependsOn("shadowJar")
}

tasks.getByName("remapJar") {
    dependsOn("shadowJar")
    mustRunAfter("shadowJar")
    //jar = tasks.getByName("shadowJar.archivePath
}
