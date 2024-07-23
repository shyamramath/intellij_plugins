plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.23"
  id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.escuela"
version = "1.1-SNAPSHOT"

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies(){
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
//  implementation("com.escuela.ai:jiraaiautomation:2.0-SNAPSHOT")
  implementation("org.dhatim:fastexcel:0.18.0")
  implementation("org.dhatim:fastexcel-reader:0.18.0")
  implementation("com.konghq:unirest-java:3.14.1")
  implementation("dev.langchain4j:langchain4j-open-ai:0.25.0")
  implementation("dev.langchain4j:langchain4j:0.25.0")
}
// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2023.2.6")
  type.set("IC") // Target IDE Platform
  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }

  patchPluginXml {
    sinceBuild.set("232")
    untilBuild.set("242.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))


  }

  publishPlugin {
//    token.set(System.getenv("PUBLISH_TOKEN"))
    token.set("perm:c2h5YW0ucmFtYXRo.OTItMTA0NTg=.TrbkBpFcXAnA2Ak4VUR6ZtkwxjCxGm")
    channels.set(listOf("beta"))
  }
}