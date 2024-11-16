plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // kapt plugin
    // alias(libs.plugins.jetbrains.kotlin.kapt)
    // ksp
    alias(libs.plugins.kotlin.ksp)
    // serialization
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.devautro.coursesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devautro.coursesapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.fragment)

    // Dagger2
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
//    kapt(libs.dagger.compiler)

    // Room
    implementation(libs.room)
    ksp(libs.room.compiler)
    implementation(libs.room.support)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Glide
    implementation(libs.glide)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    // Adapter Delegates
    implementation(libs.adapter.delegate)
    implementation(libs.adapter.delegate.viewBinding)

}