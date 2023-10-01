import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    application
}

group = "br.com.jarvis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.springframework.boot:spring-boot-starter-parent:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.0")
    implementation("org.springframework.boot:spring-boot-devtools:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.1.0")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.springframework.boot:spring-boot-maven-plugin:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.1.0")

    implementation("com.restfb:restfb:2.17.0")

    implementation("org.springframework.security:spring-security-web:6.1.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("com.auth0:java-jwt:4.4.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "17"
    }
}

application {
    mainClass.set("br/com/jarvis/MainKt")
}