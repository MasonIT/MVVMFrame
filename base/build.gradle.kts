plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = BuildVersions.compileSdkVersion

    defaultConfig {

        minSdk = BuildVersions.minSdkVersion
        targetSdk = BuildVersions.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kapt {
        useBuildCache = true
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(Dependencies.androidxCore)
    api(Dependencies.kotlinLibrary)
    api(Dependencies.kotlinCoroutinesLibrary)
    api(Dependencies.appcompat)
    api(Dependencies.googleMaterial)
    api(Dependencies.androidUtils)
    api(Dependencies.eventBus)
    api(Dependencies.recyclerView)
    api(Dependencies.constraintlayout)
    api(Dependencies.daggerAndroid)
    api(Dependencies.daggerAndroidSupport)
    api(Dependencies.okhttp)
    api(Dependencies.okhttpLogging)
    api(Dependencies.retrofit)
    api(Dependencies.retrofitGson)
    api(Dependencies.retrofitScalars)
    api(Dependencies.navigationFragmentKtx)
    api(Dependencies.viewModelKtx)
    api(Dependencies.liveDataKtx)
    api(Dependencies.navigationUiKtx)
    kapt(Dependencies.daggerCompiler)
    kapt(Dependencies.daggerAndroidCompiler)
    api(Dependencies.activityKtx)
    api(Dependencies.fragmentKtx)
    testImplementation(Dependencies.junit4)
    androidTestImplementation(Dependencies.junitEx)
    androidTestImplementation(Dependencies.espresso)
}