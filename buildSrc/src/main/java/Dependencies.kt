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
    const val targetSdk = 27
    const val compileSdk = 27
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildTools = "27.0.3"

    // Build tools and languages
    const val androidGradlePlugin = "3.0.1"
    const val kotlin = "1.2.30"

    // Google
    const val supportVersion = "27.0.2"
    const val constraintLayout = "1.0.2"
    const val playServices = "11.8.0"
    // Arch
    const val dagger = "2.15"
    const val annotations = "3.1.1"
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
    const val espresso = "2.2.2"
    const val jUnit = "4.12"
}

object Dependencies {

    // Build tools and languages
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    // Support Libraries
    const val appcompatV7 = "com.android.support:appcompat-v7:${Versions.supportVersion}"
    const val design = "com.android.support:design:${Versions.supportVersion}"
    const val cardview = "com.android.support:cardview-v7:${Versions.supportVersion}"
    const val recyclerviewV7 = "com.android.support:recyclerview-v7:${Versions.supportVersion}"
    // Constraint Layout
    const val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
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
    const val annotations = "org.glassfish:javax.annotation:${Versions.annotations}"
    // Secure Shared Preferences
    const val securePrefs = "in.co.ophio:secure-preferences:${Versions.securePrefs}"
    // BitcoinJz
    const val bitcoinJ = "org.bitcoinj:bitcoinj-core:${Versions.bitcoinJ}"
    // QR Reader
    const val qrReader = "com.github.nisrulz:qreader:${Versions.qrReader}"
    // Espresso
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    // JUnit
    const val jUnit = "junit:junit:${Versions.jUnit}"

}