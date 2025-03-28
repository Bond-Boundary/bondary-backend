import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<BootJar> {
    enabled = true
}

tasks.withType<Jar> {
    enabled = false
}

dependencies {
    implementation(project(":bondary-application"))
    implementation(project(":bondary-domain"))
    implementation(project(":bondary-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("com.mysql:mysql-connector-j")
}