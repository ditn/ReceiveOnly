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

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree
import uk.co.adambennett.receiveonly.BuildConfig
import uk.co.adambennett.receiveonly.injection.Injector


class ReceiveOnlyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.instance.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

    }

}