
import info.solidsoft.gradle.pitest.PitestPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("info.solidsoft.pitest") version "1.9.11"
}

group = "com.gilded-rose"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val arrowVersion = "1.1.5"
val pitestJunit5Plugin = "1.1.2"

dependencies {
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
    implementation("org.pitest:pitest-junit5-plugin:$pitestJunit5Plugin")
    implementation("com.groupcdg.pitest:pitest-kotlin-plugin:1.0.7")

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

configure<PitestPluginExtension> {
    junit5PluginVersion.set(pitestJunit5Plugin)
    avoidCallsTo.set(setOf("kotlin.jvm.internal"))
    mutators.set(setOf("STRONGER"))
    targetClasses.set(setOf("com.gildedrose.*"))
    targetTests.set(setOf("com.gildedrose.*"))
    threads.set(Runtime.getRuntime().availableProcessors())
    outputFormats.set(setOf("XML", "HTML"))
    mutationThreshold.set(75)
    coverageThreshold.set(60)
}