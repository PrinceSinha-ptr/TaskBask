plugins {
  id(Plugins.kotlinKapt)
}
dependencies {
  implementation(project(Modules.TASKBASK.domain))
  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.exposed)


  implementation(Libs.Micrometer.core)
  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)
  implementation("redis.clients:jedis:5.1.0")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
  implementation("io.ktor:ktor-server-core:2.3.0")


}

