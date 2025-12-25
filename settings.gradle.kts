pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CommonDungeon"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":data")
include(":ui")
include(":ui:material3")
include(":domain")
include(":ui:navigation")
include(":ui:initial")
include(":data:user")
include(":data:authorization")
include(":ui:login")
include(":ui:home")
include(":ui:characters")
include(":ui:inventory")
include(":ui:more")
include(":data:authorization-android")
include(":data:core")
include(":data:networking")
include(":data:storage")
include(":data:player")
include(":data:item")
include(":data:character")
include(":ui:core")
include(":ui:sync")
