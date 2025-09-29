import org.gradle.internal.configuration.problems.projectPathFrom

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
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_ACCESS_TOKEN").orElse(System.getenv("MAPBOX_ACCESS_TOKEN")).get()
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
//        maven { url = uri("https://maven.regulaforensics.com/RegulaDocumentReader") }
//        maven(url = "https://jitpack.io")
//        maven(url = "https://developer.huawei.com/repo/")
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_ACCESS_TOKEN").orElse(System.getenv("MAPBOX_ACCESS_TOKEN")).get()
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}

rootProject.name = "Speediz"
include(":app")
include(":core:analytic")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:worker")
include(":core:designsystem")
include(":core:testing")
