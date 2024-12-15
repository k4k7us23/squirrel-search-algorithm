plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.k4k7us23"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-math3:3.6.1")
}

kotlin {
    jvmToolchain(17)
}