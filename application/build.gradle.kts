import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
}

group = "com.gilded-rose"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val arrowVersion = "1.1.5"

dependencies {
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")

    // testing
    testImplementation(kotlin("test"))
    implementation(kotlin("script-runtime"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}