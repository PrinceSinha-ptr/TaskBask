plugins {
  id(Plugins.mavenPublish)
    kotlin("plugin.serialization") version "1.9.0" // Use your Kotlin version

}

dependencies {
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

val sourceJar = task("sourceJar", Jar::class) {
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  archiveClassifier.set("sources")
  from(project.the<SourceSetContainer>()["main"].allSource)
}

publishing {
  publications {
    create<MavenPublication>(name) {
      from(components["java"])
      artifact(sourceJar)
    }
  }

  repositories {
    maven {
      url = uri("s3://porter-maven/releases")
      authentication {
        create<AwsImAuthentication>("awsIm")
      }
    }
  }
}
