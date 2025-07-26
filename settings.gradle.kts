pluginManagement {
  repositories {


    maven("https://maven.neoforged.net/releases")

    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
//  repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
  //注意，重点在这里
  versionCatalogs {
    create("libs") {
      from(files(System.getenv("mc_proj") + "\\1_21.versions.toml"))
    }
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "latest.release"
}
include(":jean")
include(":pet-home")
include(":big-barrel")
include(":mobcatcher")
include(":infuse")
include(":damage-indicator")
include(":lovely-robot")
include(":EnchantmentCustomTable")
include(":DurabilityViewer")
include(":held-item-info")
include(":Sweeper-Maid")
include(":better-barrel")

