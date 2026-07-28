plugins {
    id("java")
}

group = "net.cubizor.cubicolor"
// Version from gradle.properties (managed by semantic-release)
version = project.findProperty("version") ?: "0.0.0-dev"

// Alt projeler için ortak yapılandırma
subprojects {
    version = rootProject.version
    group = rootProject.group

    apply(plugin = "java")
    apply(plugin = "maven-publish")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    afterEvaluate {
        extensions.configure<PublishingExtension>("publishing") {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])

                    // POM metadata
                    pom {
                        name.set("${rootProject.name} - ${project.name}")
                        description.set("Cubicolor - Modern color management library for Minecraft")
                        url.set("https://github.com/cubizor/Cubicolor")

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
                            }
                        }

                        scm {
                            connection.set("scm:git:git://github.com/cubizor/Cubicolor.git")
                            developerConnection.set("scm:git:ssh://github.com/cubizor/Cubicolor.git")
                            url.set("https://github.com/cubizor/Cubicolor")
                        }
                    }
                }
            }

            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/cubizor/Cubicolor")

                    credentials {
                        username = System.getenv("GITHUB_ACTOR") ?: findProperty("gpr.user") as String?
                        password = System.getenv("GITHUB_TOKEN") ?: findProperty("gpr.key") as String?
                    }
                }

                // Anonymously readable mirror. GitHub Packages requires a token even for public
                // packages, which Minecraft servers resolving these libs at runtime cannot supply —
                // so `publish` also writes a plain Maven layout that CI pushes to the `maven-repo`
                // branch, served over raw.githubusercontent.com without auth. See PUBLISHING.md.
                maven {
                    name = "PublicMirror"
                    url = uri(rootProject.layout.buildDirectory.dir("maven-repo"))
                }
            }
        }
    }
}
