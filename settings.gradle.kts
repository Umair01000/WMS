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
        google()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            name = "OSS-Sonatype"
        }
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            name = "OSS-Sonatype"
        }
        mavenCentral()
    }
}

rootProject.name = "WMS"
include(":app")
 