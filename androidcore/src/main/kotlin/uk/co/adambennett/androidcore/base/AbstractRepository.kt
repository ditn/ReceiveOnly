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

import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber

abstract class AbstractRepository<LocalType, in RemoteType>(
    private val remote: Single<RemoteType>,
    private val local: Maybe<LocalType>
) {

    // TODO: Add staleness checks for the data. Maybe save the timestamp in the DB, and then compare.  

    /**
     * Fetches from the local source first, before trying the remote source. If remote is triggered,
     * the data is persisted in the local store.
     *
     * @return A [Maybe] object
     */
    fun fetch(): Maybe<LocalType> = Maybe.concat(
        local.doOnSuccess { Timber.d("Loaded data from local source $it") },
        forceFetch()
    ).firstElement()

    /**
     * Forces a remote fetch and data update in the local source.
     *
     * @return A [Maybe] object
     */
    fun forceFetch(): Maybe<LocalType> = remote.map(this::mapper)
        .doOnSuccess { saveCallResult(it) }
        .doOnSuccess { Timber.d("Loaded data from remote source $it") }
        .toMaybe()

    abstract fun saveCallResult(data: LocalType)

    abstract fun mapper(data: RemoteType): LocalType

}