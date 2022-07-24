import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        // Android
        classpath(Dependencies.Gradle.androidGradlePlugin)

        // Kotlin
        classpath(Dependencies.Gradle.kotlin)
    }
}

plugins {
    id(Dependencies.Gradle.detekt) version Dependencies.Versions.detekt
    id(Dependencies.Gradle.kotlinter) version Dependencies.Versions.kotlinter
    id(Dependencies.Gradle.kover) version Dependencies.Versions.kover
    id(Dependencies.Gradle.versions) version Dependencies.Versions.gradleVersions
}

allprojects {
    repositories {
        google()
        mavenCentral()
        configurations.all {
            resolutionStrategy.force(Dependencies.Kotlin.reflect)
            resolutionStrategy.force(Dependencies.Kotlin.stdlib)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = Dependencies.Versions.jvmTarget
    }
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = Dependencies.Versions.jvmTarget
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = Dependencies.Versions.jvmTarget
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
    jacocoEngineVersion.set(Dependencies.Versions.jacoco)
    isDisabled = false
    generateReportOnCheck = true
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
    outputFormatter = "html"
}
