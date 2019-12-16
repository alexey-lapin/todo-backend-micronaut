plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

dependencies {
    implementation(project(":todo-service-api"))
}

application {
    mainClassName = "com.github.al.todobackend.Application"
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }

    register("stage") {
        dependsOn("clean", "build")
        mustRunAfter("clean")
    }
}