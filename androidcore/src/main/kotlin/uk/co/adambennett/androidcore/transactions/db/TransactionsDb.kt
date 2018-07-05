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

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomDatabase
import io.reactivex.Maybe

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionsDb : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
}

@Dao
interface TransactionDao {

    @Insert(onConflict = REPLACE)
    fun save(transaction: Transaction)

    @Insert(onConflict = REPLACE)
    fun saveAll(transactions: List<Transaction>)

    @Query("SELECT * FROM transactions WHERE hash = :hash")
    fun loadTx(hash: String): Maybe<Transaction>

    @Query("SELECT * FROM transactions")
    fun loadAll(): Maybe<List<Transaction>>
}