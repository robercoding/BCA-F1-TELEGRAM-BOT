import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("kapt") version "1.5.20"
}
group = "me.rober"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test-junit"))
//    implementation("io.github.kotlin-telegram-functionality.bot.kotlin-telegram-functionality.bot:telegram:6.0.4")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.4")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    annotationProcessor("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}