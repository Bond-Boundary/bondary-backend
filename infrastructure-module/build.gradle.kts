allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation(project(":application-module"))
    implementation(project(":domain-module"))

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // AWS
    implementation("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.0")

    // WEBFLUX
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

    runtimeOnly("com.mysql:mysql-connector-j")
}