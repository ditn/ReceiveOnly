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
import uk.co.adambennett.androidcore.base.AbstractRepository
import uk.co.adambennett.androidcore.extensions.unroll
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

    fun fetchTransactions(xPub: String): Observable<Transaction> =
        createRepository(xPub).fetch()
            .toObservable()
            .flatMapIterable { it }

    fun refreshTransactions(xPub: String): Observable<Transaction> =
        createRepository(xPub).forceFetch()
            .toObservable()
            .flatMapIterable { it }

    private fun createRepository(xPub: String): AbstractRepository<List<Transaction>, List<Tx>> =
        object : AbstractRepository<List<Transaction>, List<Tx>>(
            transactionListService.getMultiAddressObject(xPub).map { it.txs },
            transactionsDao.loadAll().filter { !it.isEmpty() }
        ) {
            override fun saveCallResult(data: List<Transaction>) {
                transactionsDao.saveAll(data)
            }

            override fun mapper(data: List<Tx>): List<Transaction> =
                data.unroll { Transaction.mapFrom(it) }
        }
}
