pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.22"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "NeoPok"

include(
        "neopok:api",
        "neopok:host",
        "neopok:kts:definition",
        "neopok:kts:extension"
)