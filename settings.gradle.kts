plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "Cubicolor"

include("cubicolor-api")
include("cubicolor-core")
include("cubicolor-text")
include("cubicolor-bukkit")
include("cubicolor-exporter")
include("cubicolor-manager")