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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adambennett.core.data.models.Tx
import kotlinx.android.synthetic.main.activity_transaction_list.*
import timber.log.Timber
import uk.co.adambennett.receiveonly.R
import uk.co.adambennett.receiveonly.injection.Injector
import uk.co.adambennett.receiveonly.ui.base.BaseActivity
import uk.co.adambennett.receiveonly.ui.states.UiState
import uk.co.adambennett.receiveonly.util.consume
import java.util.*
import javax.inject.Inject

class TransactionListActivity : BaseActivity<TransactionListView, TransactionListPresenter>(),
    TransactionListView {

    @Inject lateinit var transactionListPresenter: TransactionListPresenterImpl

    init {
        Injector.instance.getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 12/03/2017 In the future, show formatted empty balance
        toolbar.title = ""
        setSupportActionBar(toolbar)

        recyclerview.adapter = TransactionListAdapter(Collections.emptyList()) {
            Timber.d("Clicked: $it")
        }

        recyclerview.layoutManager = LinearLayoutManager(this)
        swipe_refresh.setOnRefreshListener { presenter.onTransactionsRequested() }

        onViewReady()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_transaction_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.settings -> consume { launchSettingsActivity() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateUiState(uiState: UiState) {
        setUiState(uiState)
    }

    override fun onTransactionsLoaded(transactions: List<Tx>) {
        (recyclerview.adapter as TransactionListAdapter).updateTransactions(transactions)
    }

    override fun onBalanceLoaded(balance: String) {
        collapsing_layout.title = balance
    }

    fun launchSettingsActivity() {
        TODO() // This should start a preferences page with night mode, format settings etc
    }

    fun setUiState(state: UiState) {
        when (state) {
            UiState.CONTENT -> {
                swipe_refresh.isRefreshing = false
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

    override val layoutId: Int = R.layout.activity_transaction_list

    override fun createPresenter() = transactionListPresenter

    override val view: TransactionListView = this

}
