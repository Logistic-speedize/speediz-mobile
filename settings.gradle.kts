
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
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://developer.huawei.com/repo/")
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create("basic", BasicAuthentication::class)
            }
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_ACCESS_TOKEN").orNull
                    ?: System.getenv("MAPBOX_ACCESS_TOKEN")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create("basic", BasicAuthentication::class)
            }
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_ACCESS_TOKEN").orNull
                    ?: System.getenv("MAPBOX_ACCESS_TOKEN")
            }
        }
    }
}

rootProject.name = "Speediz"
include(":app")
