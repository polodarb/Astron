pluginManagement {
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

rootProject.name = "Astron"
include(":app")
include(":core:ui")
include(":core:platform")
include(":domain")
include(":data:repository")
include(":data:database")
include(":data:network")
include(":data:preferences")
include(":data:database:impl")
include(":data:network:impl")
include(":data:preferences:impl")
include(":data:repository:impl")
include(":domain:impl")
include(":features:onboarding")
include(":core:navigation")
include(":features:asteroids")
include(":features:favorites")
include(":features:details")
include(":features:compare")
include(":features:settings")
include(":features:dangerNotify")
include(":data:model")
