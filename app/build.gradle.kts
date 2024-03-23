plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.berkaytok66.testapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.berkaytok66.testapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    buildFeatures{
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.qamarelsafadi:CurvedBottomNavigation:0.1.3")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.akexorcist:bluetoothspp:1.0.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")
    implementation ("androidx.camera:camera-core:1.3.1")
    implementation ("androidx.camera:camera-lifecycle:1.3.1")
    implementation ("androidx.camera:camera-camera2:1.4.0-alpha04")
    implementation ("androidx.camera:camera-lifecycle:1.4.0-alpha04")
    implementation ("androidx.camera:camera-view:1.4.0-alpha04")
    implementation ("io.ably:ably-android:1.2.0")
    implementation ("com.google.android.gms:play-services-ads:22.6.0")
}
