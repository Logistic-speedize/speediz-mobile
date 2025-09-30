import org.gradle.initialization.Environment
import java.util.Properties

fun getSecret(key : String): String? {
    val file = rootDir.resolve("local.default.properties")
    if ( file.exists()) {
        val properties = Properties().apply {
            load(file.inputStream())
        }
        return properties.getProperty(key)
    }
    return null
}
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
                password = getSecret("MAPBOX_ACCESS_TOKEN")
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
