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

import `in`.co.ophio.secure.core.KeyStoreKeyGenerator
import `in`.co.ophio.secure.core.ObscuredPreferencesBuilder
import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import uk.co.adambennett.androidcore.transactions.db.TransactionDao
import uk.co.adambennett.androidcore.transactions.db.TransactionsDb
import uk.co.adambennett.receiveonly.util.annotations.ForApplication
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    /**
     * Allow the Application context to be injected but require that it be annotated with
     * {@link ForApplication} to explicitly differentiate it from an Activity Context.
     */
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideTransactionsDb(): TransactionsDb = Room.databaseBuilder(
        application,
        TransactionsDb::class.java, "transactions-db"
    ).build()

    @Provides
    @Singleton
    fun provideTransactionDao(transactionsDb: TransactionsDb): TransactionDao =
        transactionsDb.transactionDao()

    @Provides
    @Singleton
    @Named("default")
    fun provideSharedPrefs(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    @Named("secure")
    fun provideSecurePrefs(): SharedPreferences {
        val key = KeyStoreKeyGenerator.get(application, application.packageName)
            .loadOrGenerateKeys()

        return ObscuredPreferencesBuilder()
            .setApplication(application)
            .obfuscateValue(true)
            .obfuscateKey(true)
            .setSharePrefFileName(Companion.XPUB_PREFS)
            .setSecret(key)
            .createSharedPrefs()
    }

    companion object {

        private const val XPUB_PREFS: String = "uk.uk.co.adambennett.xpub_prefs"

    }

}