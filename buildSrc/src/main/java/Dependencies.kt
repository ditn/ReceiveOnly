@file:Suppress("unused")

/*
*  Copyright 2017 Adam Bennett.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*          http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

object Versions {

    // Release info
    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = "android-P"
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildTools = "28.0.0-rc2"

    // Build tools and languages
    const val androidGradlePlugin = "3.2.0-alpha14"
    const val kotlin = "1.2.41"

    // Google
    const val androidX = "1.0.0-alpha1"
    const val constraintLayout = "1.1.0"
    const val architecture = "2.0.0-alpha1"
    const val playServices = "11.8.0"
    // Arch
    const val dagger = "2.15"
    const val rxKotlin = "2.2.0"
    const val rxAndroid = "2.0.2"
    // Networking
    const val okHttp = "3.7.0"
    const val retrofit = "2.3.0"
    const val moshi = "1.4.0"
    // Other
    const val securePrefs = "0.1.3"
    const val bitcoinJ = "0.14.4"
    const val qrReader = "2.0.1"
    // Testing
    const val espresso = "3.1.0-alpha1"
    const val jUnit = "4.12"
    // Logging
    const val timber = "4.6.1"
    const val kLogger = "1.4.9"
}

object Dependencies {

    // Build tools and languages
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    // Support Libraries
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidX}"
    const val design = "com.google.android.material:material:${Versions.androidX}"
    const val cardview = "androidx.cardview:cardview:${Versions.androidX}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidX}"
    // Constraint Layout
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.architecture}"
    // Room annotation processor
    const val roomCompiler = "androidx.room:room-compiler:${Versions.architecture}"
    // Room Rx Bindings
    const val roomRxJava = "androidx.room:room-rxjava2:${Versions.architecture}"
    // Paging
    const val roomPaging = "androidx.paging:paging-runtime:${Versions.architecture}"
    // Test helpers for Room
    const val roomTesting = "androidx.room:room-testing:${Versions.architecture}"
    // Play Services
    const val playServices = "com.google.android.gms:play-services-vision:${Versions.playServices}"
    // RxJava
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    // Networking
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    // Moshi
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    // Secure Shared Preferences
    const val securePrefs = "in.co.ophio:secure-preferences:${Versions.securePrefs}"
    // BitcoinJ
    const val bitcoinJ = "org.bitcoinj:bitcoinj-core:${Versions.bitcoinJ}"
    // QR Reader
    const val qrReader = "com.github.nisrulz:qreader:${Versions.qrReader}"
    // Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    // JUnit
    const val jUnit = "junit:junit:${Versions.jUnit}"
    // Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val kLogging = "io.github.microutils:kotlin-logging:${Versions.kLogger}"

}