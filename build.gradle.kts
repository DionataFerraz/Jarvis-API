import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    application
//    kotlin("kapt") version "1.6.21"
//    kotlin("plugin.allopen") version "1.6.21"
}

/*allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}*/

group = "br.com.jarvis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.springframework.boot:spring-boot-starter:2.3.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-web:2.3.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-devtools:2.3.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.3.3.RELEASE")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
    implementation("com.h2database:h2:2.1.214")
    implementation("org.springframework.boot:spring-boot-maven-plugin:2.3.3.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:2.3.3.RELEASE")
    //QUANDO ADICIONO ESSA LIB DA PAU NAS REQUESTS, FICAM TODAS COMO NÃO AUTORIZADAS, MAS EU PRECISO PARA USAR O OAuth2AccessToken
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.3.RELEASE")//AuthorizationServerTokenServices
    implementation("com.restfb:restfb:2.17.0")


/*
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")*/
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