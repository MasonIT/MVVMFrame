
internal object Versions {
    // build plugins
    const val kotlin = "1.5.30"
    // UI controller
    const val constraintLayout = "2.0.4"// NOTE: Do not update yet - If you update to 2.0.2 MotionLayout crashes app somewhere
    const val recyclerView = "1.1.0"
    // androidx
    const val kotlinCoroutines = "1.3.6"
    const val appcompat = "1.4.1"
    const val androidXCore = "1.7.0"
    const val jetpackLifecycle = "2.2.0"
    const val jetpackNavigationKtx = "2.2.0"
    const val dataBinding = "3.1.4"
    const val googleMaterial = "1.5.0"
    const val eventBus = "3.0.0"
    const val cameraXView = "1.0.0-alpha09"
    const val calendar = "3.7.1"
    // network
    const val okHttp = "3.12.2"
    const val okHttpLogging = "3.12.0"
    const val retrofit2 = "2.7.1"
    // injection
    const val dagger = "2.35.1"
    // utils
    const val blankJ = "1.31.0"
    const val espresso = "3.4.0"
    const val junit = "4.13.2"
    const val junitEx = "1.1.3"
    const val runner = "1.0.2"
    const val rules = "1.1.0"
    const val activityKtx = "1.3.0-rc01"
    const val fragmentKtx = "1.4.0-alpha04"
}

object BuildVersions {
    const val compileSdkVersion = 31
    const val minSdkVersion = 23
    const val targetSdkVersion = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Dependencies {
    const val kotlinLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutinesLibrary = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidXCore}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val googleMaterial = "com.google.android.material:material:${Versions.googleMaterial}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.jetpackLifecycle}"
    const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.jetpackLifecycle}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNavigationKtx}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNavigationKtx}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}"
    const val eventBus = "org.greenrobot:eventbus:${Versions.eventBus}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val retrofitScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit2}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val androidUtils = "com.blankj:utilcodex:${Versions.blankJ}"
    const val junit4 = "junit:junit:${Versions.junit}"
    const val junitEx = "androidx.test.ext:junit:${Versions.junitEx}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
}
