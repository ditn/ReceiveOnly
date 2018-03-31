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

package uk.co.adambennett.androidcore.base

import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class FreshFetchStrategy<LocalType, RemoteType>(emitter: SingleEmitter<LocalType>) {

    abstract val remote: Single<RemoteType>

    abstract val local: Single<LocalType>

    init {
        val firstDataDisposable = local.subscribe(emitter::onSuccess)

        remote.map(this.mapper())
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { localTypeData ->
                    firstDataDisposable.dispose()
                    saveCallResult(localTypeData)
                    local.doOnError { Timber.e(it) }
                        .subscribe(emitter::onSuccess)
                }
            )
    }

    abstract fun saveCallResult(data: LocalType)

    abstract fun mapper(): Function<RemoteType, LocalType>

}