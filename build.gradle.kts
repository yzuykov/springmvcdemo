plugins {
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "2.1.10"
	kotlin("plugin.spring") version "2.1.10"
	jacoco
}

group = "ru.yzuykov"
version = "1.0.0-SNAPSHOT"

kotlin {
	jvmToolchain(21)
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot starters
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-devtools")

	// Spring Cloud
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

	// Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Webjars
	implementation("org.webjars:webjars-locator:0.45")
	implementation("org.webjars.npm:bootstrap:5.1.3")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.mockk:mockk:1.13.13")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<JacocoReport>("jacocoTestReport") {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}
