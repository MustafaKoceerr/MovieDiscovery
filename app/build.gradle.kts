import java.util.Properties

// build.gradle.kts (module-level)'in en üstüne ekle:
val localProperties = File(rootDir, "local.properties")
val apiKey = Properties().apply {
    load(localProperties.inputStream())
}.getProperty("API_KEY") ?: throw GradleException("API_KEY not found in local.properties")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.moviediscovery"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviediscovery"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // API key'i BuildConfig'e ekliyoruz
        buildConfigField("String", "API_KEY", "${apiKey}")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    // ========================================
    // TESTING DEPENDENCIES
    // ========================================

    // Unit Testing Framework
    testImplementation(libs.junit) // Uses catalog version instead of duplicate "junit:junit:4.13.2"

    // Mockito for mocking
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // MockK for Kotlin-specific mocking (needed for BuildConfig)
    testImplementation("io.mockk:mockk:1.13.8")

    // Coroutines testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Flow testing with Turbine
    testImplementation("app.cash.turbine:turbine:1.0.0")

    // Better assertions with Truth
    testImplementation("com.google.truth:truth:1.1.4")

    // LiveData testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Hilt testing
    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    kspTest("com.google.dagger:hilt-android-compiler:2.48")

    // Mock web server for API testing
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    // ========================================
    // INSTRUMENTED TEST DEPENDENCIES
    // ========================================

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}