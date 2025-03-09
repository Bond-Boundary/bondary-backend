import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm")
	kotlin("kapt")
	kotlin("plugin.spring") apply false
	kotlin("plugin.jpa") apply false
	id("org.springframework.boot") apply false
	id("io.spring.dependency-management")
	id("org.asciidoctor.jvm.convert") apply false
	id("org.jlleitschuh.gradle.ktlint") apply false
}

java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")

allprojects {
	group = "${property("projectGroup")}"
	version = "${property("applicationVersion")}"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.kapt")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.asciidoctor.jvm.convert")
	apply(plugin = "org.jlleitschuh.gradle.ktlint")

	dependencyManagement {
		imports {
			mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:${property("springCloudDependenciesVersion")}")
		}
	}

	dependencies {
		// KOTLIN
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		// AWS
		implementation("io.awspring.cloud:spring-cloud-aws-starter")
		implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
		implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store")

		// TEST
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")

		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		kapt("org.springframework.boot:spring-boot-configuration-processor")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	tasks.withType<BootJar> {
		enabled = false
	}

	tasks.withType<Jar> {
		enabled = true
	}

	java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "${project.property("javaVersion")}"
		}
	}
}