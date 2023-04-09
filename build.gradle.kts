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
    implementation("org.springframework.boot:spring-boot-starter:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.3")
    implementation("org.springframework.boot:spring-boot-devtools:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.7.3")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.springframework.boot:spring-boot-maven-plugin:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:2.7.3")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.6.RELEASE")
    implementation("com.restfb:restfb:2.17.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}