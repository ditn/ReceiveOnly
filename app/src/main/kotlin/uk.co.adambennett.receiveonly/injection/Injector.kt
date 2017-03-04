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

package uk.co.adambennett.receiveonly.injection

import android.app.Application
import android.content.Context

open class Injector private constructor() {

    private object Holder { val INSTANCE = Injector() }

    companion object {
        val instance: Injector by lazy { Holder.INSTANCE }
    }

    private lateinit var applicationComponent: ApplicationComponent

    fun init(applicationContext: Context) {

        val applicationModule = ApplicationModule(applicationContext as Application)
        val apiModule = ApiModule()

        initAppComponent(applicationModule, apiModule)
    }

    protected fun initAppComponent(applicationModule: ApplicationModule, apiModule: ApiModule) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .apiModule(apiModule)
                .build()
    }

    fun getAppComponent(): ApplicationComponent {
        return applicationComponent
    }

}