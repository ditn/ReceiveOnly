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

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import uk.co.adambennett.androidcore.transactions.db.Transaction
import uk.co.adambennett.androidcore.transactions.repository.TransactionsRepository
import uk.co.adambennett.receiveonly.ui.base.BasePresenter
import uk.co.adambennett.receiveonly.ui.states.UiState
import uk.co.adambennett.receiveonly.util.annotations.Unscoped
import uk.co.adambennett.receiveonly.util.rxjava.addToCompositeDisposable
import uk.co.adambennett.receiveonly.util.rxjava.applySchedulers
import javax.inject.Inject

@Unscoped
class TransactionListPresenterImpl @Inject constructor(
    private val repository: TransactionsRepository
) : BasePresenter<TransactionListView>(),
    TransactionListPresenter {

    // TODO: 11/03/2017 Load xPub from encrypted storage. If not found, prompt user to add xPub
    override fun onViewReady() {
        super.onViewReady()
        fetchTransactions()
    }

    override fun onTransactionsRequested() {
        // Random xPub lifted from a Google search; has a few small repository
        repository
            .refreshTransactions("xpub6CUGRUonZSQ4TWtTMmzXdrXDtypWKiKrhko4egpiMZbpiaQL2jkwSB1icqYh2cfDfVxdx4df189oLKnC5fSwqPfgyP3hooxujYzAu3fDVmz")
            .subscribeAndUpdateUi()
    }

    private fun fetchTransactions() {
        repository.fetchTransactions("xpub6CUGRUonZSQ4TWtTMmzXdrXDtypWKiKrhko4egpiMZbpiaQL2jkwSB1icqYh2cfDfVxdx4df189oLKnC5fSwqPfgyP3hooxujYzAu3fDVmz")
            .subscribeAndUpdateUi()
    }

    private fun Observable<Transaction>.subscribeAndUpdateUi(): Disposable {
        return this.applySchedulers()
            .addToCompositeDisposable(this@TransactionListPresenterImpl)
            .doOnSubscribe { view.updateUiState(UiState.LOADING) }
            .toList()
            .subscribe(
                { list ->
                    if (list.isEmpty()) {
                        view.updateUiState(UiState.EMPTY)
                    } else {
                        view.onTransactionsLoaded(list)
                        view.updateUiState(UiState.CONTENT)
                    }
//                    val formattedTotal =
//                        BtcFixedFormat.getCodeInstance().format(response.wallet.finalBalance)
//
//                    view.onBalanceLoaded(formattedTotal)

                }, { _ ->
                    view.updateUiState(UiState.FAILED)
                }
            )
    }

}