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

package uk.co.adambennett.receiveonly.ui.transactionlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.bitcoinj.utils.BtcFormat
import uk.co.adambennett.receiveonly.R
import uk.co.adambennett.receiveonly.data.models.Tx
import java.text.SimpleDateFormat
import java.util.*

class TransactionListAdapter(val items: List<Tx>) : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    override fun onBindViewHolder(holder: TransactionViewHolder?, position: Int) {
        val tx = items[position]

        val date = Date(tx.time * 1000)
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val dateFormatted = formatter.format(date)
        holder?.date?.text = dateFormatted

        val format: String = BtcFormat.getCoinInstance(10).format(tx.result)

        holder?.amount?.text = format
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    inner class TransactionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val direction: TextView = itemView?.findViewById(R.id.direction) as TextView
        val date: TextView = itemView?.findViewById(R.id.date) as TextView
        val amount: TextView = itemView?.findViewById(R.id.amount) as TextView

    }
}
