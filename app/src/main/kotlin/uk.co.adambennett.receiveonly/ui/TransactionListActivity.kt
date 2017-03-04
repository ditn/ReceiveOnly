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

package uk.co.adambennett.receiveonly.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_transaction_list.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.adambennett.receiveonly.R
import uk.co.adambennett.receiveonly.data.api.ApiInterceptor
import uk.co.adambennett.receiveonly.data.api.BASE_API
import uk.co.adambennett.receiveonly.data.api.MultiAddress
import uk.co.adambennett.receiveonly.ui.states.UiState

class TransactionListActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        setSupportActionBar(toolbar)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ApiInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_API)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val api = retrofit.create(MultiAddress::class.java)
//        button.setOnClickListener {
//            api.getTransactions("REDACTED")
//                    .applySchedulers()
//                    .subscribe({ response ->
//                        Log.d(TAG, response.toString())
//                    }, { e ->
//                        Log.e(TAG, "Well, fuck", e)
//                    })
//        }
    }

    fun setUiState(state: UiState) {
        when (state) {
            UiState.CONTENT -> {
                recyclerview.visibility = View.VISIBLE
                loading_layout.visibility = View.GONE
            }
            UiState.LOADING -> {
                recyclerview.visibility = View.GONE
                loading_layout.visibility = View.VISIBLE
            }
            UiState.EMPTY -> TODO()
            UiState.FAILED -> TODO()
        }
    }
}
