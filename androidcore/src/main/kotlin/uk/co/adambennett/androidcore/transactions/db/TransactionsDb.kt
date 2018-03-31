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

package uk.co.adambennett.androidcore.transactions.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Single


@Database(entities = [Transaction::class], version = 1)
abstract class TransactionsDb : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}


@Dao
interface TransactionDao {

    @Insert(onConflict = REPLACE)
    fun save(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE hash = :hash")
    fun loadTx(hash: String): Single<Transaction>

    @Query("SELECT * FROM transactions")
    fun loadAll(): Single<List<Transaction>>

}