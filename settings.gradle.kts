pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Corrected line
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "quickbank"
include(":app")
