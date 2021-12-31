
repositories {
  maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
  maven("https://repo1.maven.org/maven2/")
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://oss.sonatype.org/content/repositories/central")
}

plugins {
    id ("maven-publish")
	id ("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "Bssentials-Common"
    version = "1"
    group = "com.javazilla.mods"
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}

dependencies {
  compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
  //compileOnly("cloud.commandframework", "cloud-core", "1.6.0")
}