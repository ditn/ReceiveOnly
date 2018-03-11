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

package uk.co.adambennett.core.data.services

import uk.co.adambennett.core.data.api.MultiAddress
import uk.co.adambennett.core.data.models.MultiAddressResponse
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionListService @Inject constructor(retrofit: Retrofit) {

    // TODO: Inject a fully-formed service? Not sure if necessary
    private val service: MultiAddress = retrofit.create(MultiAddress::class.java)

    /**
     * Returns an up-to-date {@link MultiAddressResponse} object
     */
    fun getMultiAddressObject(xPub: String): Single<MultiAddressResponse> =
        service.getTransactions(xPub)

}