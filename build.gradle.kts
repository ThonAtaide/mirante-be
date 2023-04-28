import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.regex.Pattern.compile

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
    }
}

plugins {
    kotlin("plugin.spring") version "1.7.21"
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.21"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.boot") version "2.7.5"
}

noArg {
    annotation("br.com.mirantebackend.annotations")
}

group = "br.com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    compile("org.springframework.cloud:spring-cloud-starter-sleuth")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.0")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.0")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("com.pinterest:ktlint:0.36.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

apply(plugin = "org.jlleitschuh.gradle.ktlint")
