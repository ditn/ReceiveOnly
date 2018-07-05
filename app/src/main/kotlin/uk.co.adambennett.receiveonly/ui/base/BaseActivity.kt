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

package uk.co.adambennett.receiveonly.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<VIEW : View, PRESENTER : Presenter<VIEW>> : AppCompatActivity(), View {

    lateinit var presenter: PRESENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(layoutId)
        presenter = createPresenter()
        presenter.init(view)
    }

    protected fun onViewReady() {
        presenter.onViewReady()
    }

    @CallSuper
    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }

    @CallSuper
    override fun onPause() {
        presenter.onViewPaused()
        super.onPause()
    }

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun createPresenter(): PRESENTER

    protected abstract val view: VIEW
}
