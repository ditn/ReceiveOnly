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

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_transaction_list.*
import uk.co.adambennett.receiveonly.R
import uk.co.adambennett.receiveonly.data.models.Tx
import uk.co.adambennett.receiveonly.ui.base.BaseActivity
import uk.co.adambennett.receiveonly.ui.states.UiState

class TransactionListActivity : BaseActivity<TransactionListView, TransactionListPresenter>(), TransactionListView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        onViewReady()
    }

    override fun updateUiState(uiState: UiState) {
        setUiState(uiState)
    }

    override fun onTransactionsLoaded(transactions: List<Tx>) {
        recyclerview.adapter = TransactionListAdapter(transactions)
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun setUiState(state: UiState) {
        when (state) {
            UiState.CONTENT -> {
                recyclerview.visibility = View.VISIBLE
                loading_layout.visibility = View.INVISIBLE
            }
            UiState.LOADING -> {
                recyclerview.visibility = View.INVISIBLE
                loading_layout.visibility = View.VISIBLE
            }
            UiState.EMPTY -> TODO()
            UiState.FAILED -> TODO()
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_transaction_list

    override fun createPresenter(): TransactionListPresenter = TransactionListPresenterImpl()

    override val view: TransactionListView
        get() = this

    override fun onPause() {
        super.onPause()
        presenter.onViewPaused()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}
