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

    implementation("com.h2database:h2:1.4.197")

    val exposedVersion = "0.32.1"

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    //Error in terminal https://trello.com/c/D3H4Ce21
    implementation("org.slf4j:slf4j-simple:1.7.30")

    //My dependencies, remove it in the future.
    val mysqlVersion = "8.0.15"
    implementation("mysql:mysql-connector-java:$mysqlVersion")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}