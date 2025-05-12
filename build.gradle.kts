buildscript {

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.5")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24") // Updated to match [versions]
        classpath ("com.google.gms:google-services:4.3.14") // Updated to match [versions]
        classpath ("com.android.tools.build:gradle:8.5.1") // Updated to match [versions]
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }

}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
}



