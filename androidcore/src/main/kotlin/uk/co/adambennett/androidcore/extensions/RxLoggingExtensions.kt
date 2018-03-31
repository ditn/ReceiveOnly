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

@file:Suppress("NOTHING_TO_INLINE")

package uk.co.adambennett.androidcore.extensions

import io.reactivex.*
import timber.log.Timber

inline fun <reified T> printEvent(tag: String, success: T?, error: Throwable?) =
    when {
        success == null && error == null -> Timber.d("$tag Complete") /* Only with Maybe */
        success != null -> Timber.d("$tag Success $success")
        error != null -> Timber.d("$tag Error $error")
        else -> Unit
    }

inline fun printEvent(tag: String, error: Throwable?) =
    when {
        error != null -> Timber.d("$tag Error $error")
        else -> Timber.d("$tag Complete")
    }

inline fun tag() =
    Thread.currentThread().stackTrace
        .first { it.fileName.endsWith(".kt") }
        .let { stack -> "${stack.fileName.removeSuffix(".kt")}::${stack.methodName}:${stack.lineNumber}" }

inline fun <reified T> Single<T>.log(): Single<T> {
    val tag = tag()
    return doOnEvent { success, error -> printEvent(tag, success, error) }
        .doOnSubscribe { Timber.d("$tag Subscribe") }
        .doOnDispose { Timber.d("$tag Dispose") }
}

inline fun <reified T> Maybe<T>.log(): Maybe<T> {
    val tag = tag()
    return doOnEvent { success, error -> printEvent(tag, success, error) }
        .doOnSubscribe { Timber.d("$tag Subscribe") }
        .doOnDispose { Timber.d("$tag Dispose") }
}

inline fun Completable.log(): Completable {
    val tag = tag()
    return doOnEvent { printEvent(tag, it) }
        .doOnSubscribe { Timber.d("$tag Subscribe") }
        .doOnDispose { Timber.d("$tag Dispose") }
}

inline fun <reified T> Observable<T>.log(): Observable<T> {
    val line = tag()
    return doOnEach { Timber.d("$line Each $it") }
        .doOnSubscribe { Timber.d("$line Subscribe") }
        .doOnDispose { Timber.d("$line Dispose") }
}

inline fun <reified T> Flowable<T>.log(): Flowable<T> {
    val line = tag()
    return doOnEach { Timber.d("$line Each $it") }
        .doOnSubscribe { Timber.d("$line Subscribe") }
        .doOnCancel { Timber.d("$line Cancel") }
}