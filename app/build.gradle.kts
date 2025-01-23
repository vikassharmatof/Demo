plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.layout"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.layout"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.code.gson:gson:2.6.2")
    implementation("com.squareup.retrofit2:retrofit:2.0.2")
    implementation("com.squareup.retrofit2:converter-gson:2.0.2")
    implementation ("com.squareup.picasso:picasso:2.8")

    implementation("com.google.dagger:hilt-android:2.51.1")


    // add below dependency for using room.
    implementation ("androidx.room:room-runtime:2.2.5")
    annotationProcessor ("androidx.room:room-compiler:2.2.5")


// add below dependency for using lifecycle extensions for room.
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    annotationProcessor ("androidx.lifecycle:lifecycle-compiler:2.2.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")


    implementation ("com.microsoft.identity.client:msal:2.0.1")

    implementation ("com.android.volley:volley:1.2.1")

    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))
}
