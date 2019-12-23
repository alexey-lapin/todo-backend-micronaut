plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("net.ltgt.apt-eclipse") version "0.21"
}

val micronautVersion = "1.3.0.M1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.10")
    compileOnly("org.projectlombok:lombok:1.18.10")

    annotationProcessor(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    annotationProcessor("io.micronaut:micronaut-inject-java")
    annotationProcessor("io.micronaut:micronaut-validation")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")

    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut.configuration:micronaut-jdbc-tomcat")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-validation")
    implementation("javax.annotation:javax.annotation-api")

    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
    runtimeOnly("com.h2database:h2")

    testAnnotationProcessor(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testAnnotationProcessor("io.micronaut:micronaut-inject-java")

    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.assertj:assertj-core:3.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "com.github.al.todobackend.Application"
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }

    test {
        useJUnitPlatform()
    }

    // used by Heroku
    register("stage") {
        dependsOn("clean", "build")
        mustRunAfter("clean")
    }
}