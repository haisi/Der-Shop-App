plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    // Annotation processing with Kotlin
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
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
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.6.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.5")
    implementation("com.jakewharton.timber:timber:4.7.1")

    implementation("com.google.firebase:firebase-core:18.0.0")
    implementation("com.google.firebase:firebase-crashlytics-ndk:17.3.0")

//    implementation fileTree(dir: 'libs', include: ['*.jar'])

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

    // Testing
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

detekt {
    config = files("${rootProject.projectDir}/config/detekt_config.yml")
    buildUponDefaultConfig = true
    baseline = file("${rootProject.projectDir}/config/detekt_baseline.xml")
    autoCorrect = true
}
