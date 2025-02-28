plugins {
  kotlin("plugin.serialization") version "1.9.0" // Use your Kotlin version
}

dependencies {
  implementation(Libs.KotlinUtils.openTracing)
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
  implementation("io.ktor:ktor-server-core:2.3.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

}
