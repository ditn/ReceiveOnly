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

import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.co.adambennett.core.data.models.Tx

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val hash: String,
    val ver: Int,
    val txIndex: Int,
    val doubleSpend: Boolean,
    val result: Long,
    val balance: Long,
    val time: Long,
    val blockHeight: Long
) {

    companion object {

        fun mapFrom(tx: Tx): Transaction = Transaction(
            tx.hash,
            tx.ver,
            tx.txIndex,
            tx.doubleSpend,
            tx.result,
            tx.balance,
            tx.time,
            tx.blockHeight
        )
    }

}
