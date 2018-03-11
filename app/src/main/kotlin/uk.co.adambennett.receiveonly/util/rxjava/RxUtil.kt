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

package uk.co.adambennett.receiveonly.util.rxjava

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.adambennett.receiveonly.ui.base.BasePresenter


/**
 * Applies standard Schedulers to an [Observable], ie IO for subscription, Main Thread for
 * onNext/onComplete/onError.
 */
fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

/**
 * Applies standard Schedulers to a [Single], ie IO for subscription, Main Thread for
 * onNext/onComplete/onError.
 */
fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

/**
 * Applies standard Schedulers to a [Completable], ie IO for subscription,
 * Main Thread for onNext/onComplete/onError.
 */
fun Completable.applySchedulers(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

/**
 * Places subscription into the [CompositeDisposable] belonging to a [BasePresenter] for memory
 * safety.
 */
fun <T> Observable<T>.addToCompositeDisposable(presenter: BasePresenter<*>): Observable<T> {
    return doOnSubscribe { it -> presenter.compositeDisposable.add(it) }
}

/**
 * Places subscription into the [CompositeDisposable] belonging to a [BasePresenter] for memory
 * safety.
 */
fun <T> Single<T>.addToCompositeDisposable(presenter: BasePresenter<*>): Single<T> {
    return doOnSubscribe { it -> presenter.compositeDisposable.add(it) }
}
