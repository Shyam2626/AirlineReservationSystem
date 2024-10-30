import org.jetbrains.kotlin.builtins.StandardNames.FqNames.target

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("nu.studer.jooq") version "8.1"
}

group = "com.shyam"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.liquibase:liquibase-core")
	jooqGenerator("mysql:mysql-connector-java:8.0.33")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	version.set("3.18.5")
	configurations {
		create("main") {
			jooqConfiguration.apply {
				jdbc.apply {
					driver = "com.mysql.cj.jdbc.Driver"
					url = "jdbc:mysql://localhost:3306/AirlineReservationSystem"
					user = "root"
					password = "root"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"  // Use KotlinGenerator for Kotlin classes
					database.apply {
						name = "org.jooq.meta.mysql.MySQLDatabase"
						inputSchema = "AirlineReservationSystem"
						includes = ".*"
						excludes = ""
					}
					generate.apply {
						isRecords = true
						isDaos = true
						isPojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = ""
						directory = "target"
					}
				}
			}
		}
	}
}