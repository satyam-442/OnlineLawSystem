pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://jcenter.bintray.com")
        }
    }
}

rootProject.name = "Online Law System"
include(":app")
 