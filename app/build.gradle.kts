plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id ("kotlin-kapt")
}

android {
    compileSdk = BuildVersions.compileSdkVersion

    defaultConfig {
        applicationId = "com.punkstudio.mvvmframe"
        minSdk = BuildVersions.minSdkVersion
        targetSdk = BuildVersions.targetSdkVersion
        versionCode = BuildVersions.versionCode
        versionName = BuildVersions.versionName
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation (Dependencies.androidxCore)
    implementation (Dependencies.appcompat)
    implementation (Dependencies.googleMaterial)
    implementation (Dependencies.constraintlayout)
    implementation (project(":base"))
    testImplementation (Dependencies.junit4)
    androidTestImplementation (Dependencies.junitEx)
    androidTestImplementation (Dependencies.espresso)
}