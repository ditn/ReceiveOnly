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

package uk.co.adambennett.androidcore.extensions

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Applies standard Schedulers to an [Observable], ie IO for subscription, Main Thread for
 * onNext/onComplete/onError.
 */
fun <T> Observable<T>.applySchedulers(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * Applies standard Schedulers to a [Single], ie IO for subscription, Main Thread for
 * onNext/onComplete/onError.
 */
fun <T> Single<T>.applySchedulers(): Single<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * Applies standard Schedulers to a [Maybe], ie IO for subscription, Main Thread for
 * onNext/onComplete/onError.
 */
fun <T> Maybe<T>.applySchedulers(): Maybe<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * Applies standard Schedulers to a [Completable], ie IO for subscription,
 * Main Thread for onNext/onComplete/onError.
 */
fun Completable.applySchedulers(): Completable =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * Adds the subscription to the upstream [Observable] to the [CompositeDisposable]
 * supplied by a class implementing [MemorySafeSubscription]. This allows the subscription to be
 * cancelled automatically by the Presenter on Android lifecycle events.
 *
 * @param impl A class implementing [MemorySafeSubscription]
 * @param <T>       The type of the upstream [Observable]
 */
fun <T> Observable<T>.addToCompositeDisposable(impl: MemorySafeSubscription): Observable<T> =
        this.doOnSubscribe { impl.compositeDisposable.add(it) }

/**
 * Adds the subscription to the upstream [Completable] to the [CompositeDisposable] supplied by a
 * class implementing [MemorySafeSubscription]. This allows the subscription to be cancelled automatically by
 * the Presenter on Android lifecycle events.
 *
 * @param impl A class implementing [MemorySafeSubscription]
 */
fun Completable.addToCompositeDisposable(impl: MemorySafeSubscription): Completable =
        this.doOnSubscribe { impl.compositeDisposable.add(it) }

/**
 * Adds the subscription to the upstream [Single] to the [CompositeDisposable]
 * supplied by a class implementing [MemorySafeSubscription]. This allows the subscription to be
 * cancelled automatically by the Presenter on Android lifecycle events.
 *
 * @param impl A class implementing [MemorySafeSubscription]
 * @param <T>       The type of the upstream [Single]
 */
fun <T> Single<T>.addToCompositeDisposable(impl: MemorySafeSubscription): Single<T> =
        this.doOnSubscribe { impl.compositeDisposable.add(it) }

interface MemorySafeSubscription {

    val compositeDisposable: CompositeDisposable

}