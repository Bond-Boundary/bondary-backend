allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation(project(":application-module"))
    implementation(project(":domain-module"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.0")


    runtimeOnly("com.mysql:mysql-connector-j")
}