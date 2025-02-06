import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-parcelize")
}

val apiKeyPropertiesFile = rootProject.file("apikey.properties")
val apiKeyProperties = Properties()

if (apiKeyPropertiesFile.exists()) {
    apiKeyProperties.load(FileInputStream(apiKeyPropertiesFile))
}

fun getApiKeyProperty(key: String): String {
    return apiKeyProperties.getProperty(key) ?: throw IllegalArgumentException("Missing property: $key")
}

android {
    namespace = AppConfig.id
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.id
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("type")
    productFlavors {
        create("dev") {
            dimension = "type"
            buildConfigField("String", "BASE_URL", "\"${getApiKeyProperty("BASE_URL")}\"")
            buildConfigField("String", "GITHUB_TOKEN", "\"${getApiKeyProperty("GITHUB_TOKEN")}\"")
        }

        create("prod") {
            dimension = "type"
            buildConfigField("String", "BASE_URL", "\"${getApiKeyProperty("BASE_URL")}\"")
            buildConfigField("String", "GITHUB_TOKEN", "\"${getApiKeyProperty("GITHUB_TOKEN")}\"")
        }
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName =
                    "githubu_${AppConfig.versionName}(${AppConfig.versionCode}).${output.outputFile.extension}"
            }
        }
    }
}

dependencies {
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(AppDependencies.appLibraries)
    //test libs
    testImplementation(AppDependencies.testLibraries)

    kapt(AppDependencies.compilerLibraries)

    androidTestImplementation(AppDependencies.androidTestLibraries)

    debugImplementation(AppDependencies.chuckerDebug)
    releaseImplementation(AppDependencies.chuckerRelease)
}