plugins {
    application
    id("net.ltgt.apt-eclipse") version "0.21"
}

val micronautVersion = "1.3.0.M1"

allprojects {
    apply(plugin = "application")
    apply(plugin = "net.ltgt.apt-eclipse")
    apply(plugin = "jacoco")

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
        implementation("io.micronaut:micronaut-http-client")
        implementation("io.micronaut:micronaut-inject")
        implementation("io.micronaut:micronaut-validation")
        implementation("io.micronaut:micronaut-runtime")
        implementation("io.micronaut:micronaut-http-server-netty")
        implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
        implementation("javax.annotation:javax.annotation-api")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

        testAnnotationProcessor(platform("io.micronaut:micronaut-bom:$micronautVersion"))
        testAnnotationProcessor("io.micronaut:micronaut-inject-java")

        testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("io.micronaut.test:micronaut-test-junit5")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}
