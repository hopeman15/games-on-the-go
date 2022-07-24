import Dependencies.Android

plugins {
    id("com.android.application")
    kotlin("android")

    // Quality Gates
    id(Dependencies.Gradle.detekt)
    id(Dependencies.Gradle.kotlinter)
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildToolsVersion

    val ciBuild = System.getenv("CI") == "true"
    defaultConfig {
        applicationId = "com.hello.curiosity.gotg.android"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        versionCode = 1
        if (ciBuild) {
            versionCode = System.getenv("GITHUB_RUN_NUMBER").toInt()
        }

        versionName = "${Android.versionMajor}.${Android.versionMinor}.${Android.versionPatch}"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Clear app state after each test
        testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    flavorDimensions.addAll(listOf("all"))
    productFlavors {
        create("production") {
            dimension = "all"
        }
        create("staging") {
            dimension = "all"
            versionNameSuffix = "-staging"
            applicationIdSuffix = ".staging"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }

    kotlinOptions {
        jvmTarget = Dependencies.Versions.jvmTarget
    }

    lint {
        checkDependencies = true
        abortOnError = true
        showAll = true
        textReport = true
        xmlReport = false
        htmlReport = true
        warningsAsErrors = true
        disable += mutableSetOf(
            "GoogleAppIndexingWarning",
            "GradleDependency",
            "ObsoleteLintCustomCheck"
        )
    }
}

dependencies {
    implementation(project(":shared"))

    // Android
    implementation(Android.material)
    implementation(Dependencies.Androidx.appcompat)
    implementation(Dependencies.Androidx.constraint)

    // Leak
    debugImplementation(Dependencies.leakCanary)
}
