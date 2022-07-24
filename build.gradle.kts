import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("com.android.tools.build:gradle:7.2.1")
    }
}

plugins {
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.koverMergedHtmlReport {
    enabled = true
    htmlReportDir.set(layout.buildDirectory.dir("$buildDir/reports/kover/html-result"))
}

tasks.koverXmlReport {
    isEnabled = true
    xmlReportFile.set(file("$buildDir/reports/kover/result.xml"))
}

kover {
    jacocoEngineVersion.set("0.8.8")
    isDisabled = false
    generateReportOnCheck = true
}
