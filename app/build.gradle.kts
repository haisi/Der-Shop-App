import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    // Annotation processing with Kotlin
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
    id("dagger.hilt.android.plugin")
}

val envFile = rootProject.file(".env")
val envProperties = Properties()
if (envFile.exists()) {
    envProperties.load(FileInputStream(envFile))
} else {
    envProperties["NY_TIMES_API_KEY"] = System.getenv("NY_TIMES_API_KEY")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "li.selman.dershop"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders = mapOf(
            "crashlyticsEnabled" to false
        )

        buildConfigField("String", "NY_TIMES_API_KEY", envProperties["NY_TIMES_API_KEY"].toString())
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("mock")
        create("dev")
        create("prod")
    }

    // Use suffixes for non-prod builds
    productFlavors.forEach { flavor ->
        if (flavor.name.equals("prod", true)) return@forEach

        flavor.versionNameSuffix = "-${flavor.name}"
        flavor.applicationIdSuffix = ".${flavor.name}"
    }

    // Filter Release variants for dev flavors
    val devFlavors = listOf("dev", "mock")
    variantFilter {
        val isDevFlavor = flavors.any { devFlavors.contains(it.name) }
        if (buildType.name == "release" && isDevFlavor) {
            ignore = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.6.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.6")
    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("com.google.firebase:firebase-core:18.0.0")
    implementation("com.google.firebase:firebase-crashlytics-ndk:17.3.0")

    // Support libraries
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("com.google.android.material:material:1.2.1")

    // Lifecycle
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Android KTX
    implementation("androidx.core:core-ktx:1.3.2")

    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.3.2")
    implementation("androidx.navigation:navigation-ui:2.3.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.2")

    // Image
    implementation("com.github.bumptech.glide:glide:4.11.0")

    // Paging 3
    implementation("androidx.paging:paging-runtime:3.0.0-alpha11")

    // DI
    val hilt_version = "2.28-alpha"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    val hilt_viewmodels = "1.0.0-alpha01"
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$hilt_viewmodels")
    kapt("androidx.hilt:hilt-compiler:$hilt_viewmodels")

    // Async
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    // HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0") // TODO use moshi-kotlin-codegen instead?
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // DB
    implementation("androidx.room:room-runtime:2.2.6")
    implementation("androidx.room:room-ktx:2.2.6")
    kapt("androidx.room:room-compiler:2.2.6")

    // Testing
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")
}

detekt {
    config = files("${rootProject.projectDir}/config/detekt_config.yml")
    buildUponDefaultConfig = true
    baseline = file("${rootProject.projectDir}/config/detekt_baseline.xml")
    autoCorrect = true
}
