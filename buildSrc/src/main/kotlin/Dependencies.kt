import java.util.Locale

object Dependencies {
    object Versions {
        const val agp = "7.2.1"
        const val androidx = "1.4.2"
        const val constraint = "2.1.4"
        const val gradleVersions = "0.42.0"
        const val jacoco = "0.8.8"
        const val jvmTarget = "11"
        const val kotlin = "1.7.0"
        const val material = "1.6.1"

        // Quality Gates
        const val detekt = "1.21.0"
        const val kotlinter = "3.11.1"
        const val kover = "0.5.1"
    }

    object Android {
        const val versionMajor = 0
        const val versionMinor = 1
        const val versionPatch = 0

        const val compileSdk = 32
        const val buildToolsVersion = "31.0.0"

        const val minSdk = 23
        const val targetSdk = 32

        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Androidx {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    }

    object Gradle {
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.agp}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

        // Quality Gates
        const val detekt = "io.gitlab.arturbosch.detekt"
        const val kotlinter = "org.jmailen.kotlinter"
        const val kover = "org.jetbrains.kotlinx.kover"
        const val versions = "com.github.ben-manes.versions"
    }

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.9.1"
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = setOf("RELEASE", "FINAL", "GA").any { version.toUpperCase(Locale.ROOT).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
