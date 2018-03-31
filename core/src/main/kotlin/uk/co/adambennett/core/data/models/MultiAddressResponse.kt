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

package uk.co.adambennett.core.data.models

import com.squareup.moshi.Json

data class MultiAddressResponse(
    @Json(name = "recommend_include_fee") val recommendIncludeFee: Boolean,
    @Json(name = "sharedcoin_endpoint") val sharedcoinEndpoint: String,
    val info: Info,
    val wallet: Wallet,
    val addresses: List<Address>,
    val txs: List<Tx>
)

data class Info(
    val nconnected: Int,
    val conversion: Double,
    @Json(name = "symbol_local") val symbolLocal: SymbolLocal,
    @Json(name = "symbol_btc") val symbolBtc: SymbolBtc,
    @Json(name = "latest_block") val latestBlock: LatestBlock
)

data class Wallet(
    @Json(name = "n_tx") val nTx: Int,
    @Json(name = "n_tx_filtered") val nTxFiltered: Int,
    @Json(name = "total_received") val totalReceived: Long,
    @Json(name = "total_sent") val totalSent: Long,
    @Json(name = "final_balance") val finalBalance: Long
)


data class Address(
    val address: String,
    @Json(name = "n_tx") val n_tx: Int,
    @Json(name = "total_received") val totalReceived: Long,
    @Json(name = "total_sent") val totalSent: Long,
    @Json(name = "final_balance") val finalBalance: Long,
    @Json(name = "gap_limit") val gapLimit: Int,
    @Json(name = "change_index") val changeIndex: Int,
    @Json(name = "account_index") val accountIndex: Int
)

data class Tx(
    val hash: String,
    val ver: Int,
    @Json(name = "vin_sz") val vinSz: Int,
    @Json(name = "vout_sz") val voutSz: Int,
    val size: Int,
    @Json(name = "relayed_by") val relayedBy: String,
    @Json(name = "lock_time") val lockTime: Long,
    @Json(name = "tx_index") val txIndex: Int,
    @Json(name = "double_spend") val doubleSpend: Boolean,
    val result: Long,
    val balance: Long,
    val time: Long,
    @Json(name = "block_height") val blockHeight: Long,
    val inputs: List<Input>,
    val out: List<Out>
)

data class Input(
    @Json(name = "prev_out") val prevOut: PrevOut,
    val sequence: Long,
    val script: String
)

data class Out(
    val value: Long,
    @Json(name = "tx_index") val txIndex: Long,
    val n: Int,
    val spent: Boolean,
    val script: String,
    val type: Int,
    val addr: String,
    val xpub: Xpub
)

data class PrevOut(
    val value: Long,
    @Json(name = "tx_index") val txIndex: Long,
    val n: Int,
    val spent: Boolean,
    val script: String,
    val type: Int,
    val addr: String,
    val xpub: Xpub
)

data class SymbolLocal(
    val code: String,
    val symbol: String,
    val name: String,
    val conversion: Double,
    @Json(name = "symbol_appears_after") val symbolAppearsAfter: Boolean,
    val local: Boolean
)

data class SymbolBtc(
    val code: String,
    val symbol: String,
    val name: String,
    val conversion: Double,
    @Json(name = "symbol_appears_after") val symbolAppearsAfter: Boolean,
    val local: Boolean
)

data class LatestBlock(
    @Json(name = "block_index") val blockIndex: Long,
    val hash: String,
    val height: Long,
    val time: Long
)

data class Xpub(
    val m: String,
    val path: String
)

