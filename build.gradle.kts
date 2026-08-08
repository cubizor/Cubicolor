import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.37.0" apply false
}

group = "net.cubizor.cubicolor"
// Version from gradle.properties (managed by semantic-release)
version = project.findProperty("version") ?: "0.0.0-dev"

// Alt projeler için ortak yapılandırma
subprojects {
    version = rootProject.version
    group = rootProject.group

    apply(plugin = "java")
    apply(plugin = "com.vanniktech.maven.publish")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    extensions.configure<MavenPublishBaseExtension> {
        // Central rejects deployments without sources + javadoc jars.
        this.configure(JavaLibrary(javadocJar = JavadocJar.Javadoc(), sourcesJar = true))

        // Single aggregated deployment per build; released without a manual portal click.
        publishToMavenCentral(automaticRelease = true)

        // Central rejects unsigned artifacts. Credentials come from
        // ORG_GRADLE_PROJECT_signingInMemoryKey / ...KeyPassword in CI.
        signAllPublications()

        pom {
            name.set("${rootProject.name} - ${project.name}")
            description.set("Cubicolor - Modern color management library for Minecraft")
            url.set("https://github.com/cubizor/Cubicolor")
            inceptionYear.set("2025")

            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }

            developers {
                developer {
                    id.set("cubizor")
                    name.set("Cubizor")
                    url.set("https://github.com/cubizor")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/cubizor/Cubicolor.git")
                developerConnection.set("scm:git:ssh://github.com/cubizor/Cubicolor.git")
                url.set("https://github.com/cubizor/Cubicolor")
            }
        }
    }

    extensions.configure<PublishingExtension>("publishing") {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/cubizor/Cubicolor")

                credentials {
                    username = System.getenv("GITHUB_ACTOR") ?: findProperty("gpr.user") as String?
                    password = System.getenv("GITHUB_TOKEN") ?: findProperty("gpr.key") as String?
                }
            }
        }
    }
}

// Entry point for semantic-release (see .releaserc.json). Plain `publish` only stages the
// Central deployment; `publishAndReleaseToMavenCentral` is what actually releases it. Every
// module stages into one bundle, so a run produces a single Central deployment.
tasks.register("publishRelease") {
    group = "publishing"
    description = "Publishes all modules to Maven Central and GitHub Packages."
    dependsOn(subprojects.map { "${it.path}:publishAndReleaseToMavenCentral" })
    dependsOn(subprojects.map { "${it.path}:publishAllPublicationsToGitHubPackagesRepository" })
}
