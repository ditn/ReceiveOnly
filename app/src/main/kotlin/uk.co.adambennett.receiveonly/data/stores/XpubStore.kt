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

package uk.co.adambennett.receiveonly.data.stores

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XpubStore @Inject constructor(private val securePrefs: SharedPreferences) {

    fun storeXpub(xPub: String) {
        securePrefs.edit().putString(Companion.KEY_X_PUB, xPub).apply()
    }

    fun retrieveXpub(): String? = securePrefs.getString(Companion.KEY_X_PUB, null)

    companion object {
        private const val KEY_X_PUB = "uk.co.adambennett.key_x_pub"
    }

}