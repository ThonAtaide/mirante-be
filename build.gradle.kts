
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.21"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.7.21"
//    kotlin("kapt") version "1.4.10"
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
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.0")
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.0")

    implementation ("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation ("ch.qos.logback:logback-classic:1.2.6")

//    implementation("com.querydsl:querydsl-apt")
//    implementation("com.querydsl:querydsl-mongodb:5.0.0"){
//        exclude(group = "org.mongodb", module = "mongo-java-driver")
//    }
//    kapt("org.springframework.boot:spring-boot-configuration-processor")
//    kapt("com.querydsl:querydsl-apt")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

//kapt {
//    annotationProcessor("org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor")
//}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
