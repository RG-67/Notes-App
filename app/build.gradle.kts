import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.project.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.notesapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0.1"

        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val apiKey = localProperties.getProperty("TOKEN_KEY") ?: ""
        buildConfigField("String", "TOKEN_KEY", "\"$apiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /*ksp*/
    ksp(libs.symbol.processing.api)

    /*dagger-hilt*/
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    /*lifecycle-viewmodel-livedata*/
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    /*retrofit*/
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    /*room-database*/
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler.v250)

    /*kotlin-coroutine*/
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    /*navigation*/
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    /*balloon*/
    implementation(libs.balloon)
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(8))
    }
}