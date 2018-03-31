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

package uk.co.adambennett.androidcore.transactions.repository

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import uk.co.adambennett.androidcore.base.FreshFetchStrategy
import uk.co.adambennett.androidcore.transactions.db.Transaction
import uk.co.adambennett.androidcore.transactions.db.TransactionDao
import uk.co.adambennett.core.data.models.Tx
import uk.co.adambennett.core.data.services.TransactionListService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionsRepository @Inject constructor(
    private val transactionsDao: TransactionDao,
    private val transactionListService: TransactionListService
) {

    fun getTransactions(xPub: String): Observable<Transaction> {
        return Single.create<List<Transaction>> { emitter ->
            object : FreshFetchStrategy<List<Transaction>, List<Tx>>(emitter) {
                override val remote: Single<List<Tx>>
                    get() = transactionListService.getMultiAddressObject(xPub).map { it.txs }
                override val local: Single<List<Transaction>>
                    get() = transactionsDao.loadAll()

                override fun saveCallResult(data: List<Transaction>) {
                    data.forEach { transactionsDao.save(it) }
                }

                override fun mapper(): Function<List<Tx>, List<Transaction>> =
                    Function { it.map { Transaction.mapFrom(it) } }

            }
        }.toObservable()
            .flatMapIterable { it }
    }

//    return Observable.concatArray(
//    getUsersFromDb(),
//    getUsersFromApi()
//    .materialize()
//    .filter{ !it.isOnError }
//    .dematerialize<List<User>>()
//    )

}