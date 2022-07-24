plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32

    val ciBuild = System.getenv("CI") == "true"
    defaultConfig {
        applicationId = "com.hello.curiosity.gotg.android"
        minSdk = 21
        targetSdk = 32

        versionCode = 1
        if (ciBuild) {
            versionCode = System.getenv("GITHUB_RUN_NUMBER").toInt()
        }

        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Clear app state after each test
        testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
    }

    buildTypes {
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
        jvmTarget = "11"
    }

    lint {
        checkDependencies = true
        abortOnError = true
        showAll = true
        textReport = true
        xmlReport = false
        htmlReport = true
        warningsAsErrors = true
        disable += mutableSetOf("GoogleAppIndexingWarning", "GradleDependency", "ObsoleteLintCustomCheck")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
}
