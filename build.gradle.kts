// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // kapt
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    // ksp
    alias(libs.plugins.kotlin.ksp) apply false
    // safeArgs
    alias(libs.plugins.navigation.safeArgs) apply false
}