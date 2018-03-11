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

import com.adambennett.core.data.services.TransactionListService
import org.bitcoinj.utils.BtcFixedFormat
import uk.co.adambennett.receiveonly.ui.base.BasePresenter
import uk.co.adambennett.receiveonly.ui.states.UiState
import uk.co.adambennett.receiveonly.util.annotations.Unscoped
import uk.co.adambennett.receiveonly.util.rxjava.addToCompositeDisposable
import uk.co.adambennett.receiveonly.util.rxjava.applySchedulers
import javax.inject.Inject

@Unscoped
class TransactionListPresenterImpl @Inject constructor(
    private val transactionListService: TransactionListService
): BasePresenter<TransactionListView>(),
    TransactionListPresenter {

    // TODO: 11/03/2017 Load xPub from encrypted storage. If not found, prompt user to add xPub
    override fun onViewReady() {
        super.onViewReady()
        onTransactionsRequested()
    }

    override fun onTransactionsRequested() {
        transactionListService
            // Random xPub lifted from a Google search; has a few small transactions
            .getMultiAddressObject("xpub6CUGRUonZSQ4TWtTMmzXdrXDtypWKiKrhko4egpiMZbpiaQL2jkwSB1icqYh2cfDfVxdx4df189oLKnC5fSwqPfgyP3hooxujYzAu3fDVmz")
            .applySchedulers()
            .addToCompositeDisposable(this)
            .doOnSubscribe { view.updateUiState(UiState.LOADING) }
            .subscribe(
                { response ->
                    if (response.txs.isEmpty()) {
                        view.updateUiState(UiState.EMPTY)
                    } else {
                        view.onTransactionsLoaded(response.txs)
                        view.updateUiState(UiState.CONTENT)
                    }
                    val formattedTotal =
                        BtcFixedFormat.getCodeInstance().format(response.wallet.finalBalance)

                    view.onBalanceLoaded(formattedTotal)

                }, { _ ->
                    view.updateUiState(UiState.FAILED)
                }
            )
    }

}