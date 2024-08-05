plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.graalvm.buildtools.native") version "0.10.2"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.lnbiuc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.7")
    implementation("cn.hutool:hutool-core:5.8.28")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.3.2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation ("cn.hutool:hutool-all:5.8.25")
    implementation("io.github.linpeilie:mapstruct-plus-spring-boot-starter:1.4.3")
    implementation("io.github.linpeilie:mapstruct-plus-processor:1.4.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.2")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    implementation("com.tencentcloudapi:tencentcloud-sdk-java-live:3.1.1071")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.52")

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
