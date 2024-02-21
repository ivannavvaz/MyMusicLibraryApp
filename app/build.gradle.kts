plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.inavarro.mibibliotecamusical"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.inavarro.mibibliotecamusical"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable  = true
    }
}

dependencies {
    implementation("com.google.firebase:firebase-storage:20.3.0")
    val lifecycleVersion = "2.6.2"
    val glideVersion = "4.12.0"
    val coroutinesVersion = "1.6.4"
    val livedataVersion = "2.4.0"
    val retrofitVersion = "2.9.0"
    val lifecycleruntimektxVersion = "2.5.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$livedataVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

    // Gson
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Lifecycle Runtime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleruntimektxVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}