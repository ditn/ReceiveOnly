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

import uk.co.adambennett.receiveonly.data.services.TransactionListService
import uk.co.adambennett.receiveonly.injection.Injector
import uk.co.adambennett.receiveonly.ui.base.BasePresenter
import uk.co.adambennett.receiveonly.ui.states.UiState
import uk.co.adambennett.receiveonly.util.rxjava.addToCompositeDisposable
import uk.co.adambennett.receiveonly.util.rxjava.applySchedulers
import javax.inject.Inject

class TransactionListPresenterImpl : BasePresenter<TransactionListView>(), TransactionListPresenter {

    @Inject lateinit var transactionListService: TransactionListService

    init {
        Injector.instance.getAppComponent().inject(this)
    }

    override fun onViewReady() {
        super.onViewReady()

        transactionListService
                .getMultiAddressObject("REDACTED")
                .applySchedulers()
                .addToCompositeDisposable(this)
                .doOnSubscribe { view.updateUiState(UiState.LOADING) }
                .subscribe({ response ->
                    if (response.txs.isEmpty()) {
                        view.updateUiState(UiState.EMPTY)
                    } else {
                        view.onTransactionsLoaded(response.txs)
                        view.updateUiState(UiState.CONTENT)
                    }
                }, { _ ->
                    view.updateUiState(UiState.FAILED)
                })
    }

}