package uk.co.adambennett.receiveonly.slices

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uk.co.adambennett.androidcore.extensions.MemorySafeSubscription
import uk.co.adambennett.androidcore.extensions.addToCompositeDisposable
import uk.co.adambennett.core.data.models.Currency
import uk.co.adambennett.core.data.services.PriceService
import uk.co.adambennett.receiveonly.slices.di.SlicesInjector
import javax.inject.Inject

/**
 * Displays URIs in the format slice-content://uk.co.adambennett.receiveonly/prices/$currency
 */
class CryptoPriceSliceProvider : SliceProvider(), MemorySafeSubscription {

    override val compositeDisposable = CompositeDisposable()
    @Inject lateinit var priceService: PriceService
    private var bitcoinPrice: Double? = null
    private var etherPrice: Double? = null
    private var bitcoinCashPrice: Double? = null

    /**
     * Instantiate any required objects. Return true if the provider was successfully created,
     * false otherwise.
     */
    override fun onCreateSliceProvider(): Boolean {
        SlicesInjector.instance.getSlicesComponent().inject(this)
        return true
    }

    /**
     * Converts URL to content URI (i.e. content://uk.co.adambennett.receiveonly.slices...)
     */
    override fun onMapIntentToUri(intent: Intent?): Uri {
        // Note: implementing this is only required if you plan on catching URL requests.
        // This is an example solution.
        var uriBuilder: Uri.Builder = Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)
        if (intent == null) return uriBuilder.build()
        val data = intent.data
        if (data != null && data.path != null) {
            val path = data.path.replace("/prices", "")
            uriBuilder = uriBuilder.path(path)
        }
        val context = context
        if (context != null) {
            uriBuilder = uriBuilder.authority(context.packageName)
        }
        return uriBuilder.build()
    }

    /**
     * Construct the Slice and bind data if available.
     */
    override fun onBindSlice(sliceUri: Uri): Slice? {
        // Path recognized. Customize the Slice using the androidx.slice.builders API.
        // Note: ANR and StrictMode are enforced here so don't do any heavy operations.
        // Only bind data that is currently available in memory.
        val context = context ?: return null

        val price = when {
            sliceUri.path == "/prices/btc" -> bitcoinPrice
            sliceUri.path == "/prices/eth" -> etherPrice
            sliceUri.path == "/prices/bch" -> bitcoinCashPrice
            else -> throw IllegalArgumentException("Unknown URI $sliceUri")
        }

        return if (price == null) {
            createSliceShowingLoading(sliceUri)
        } else {
            val symbol = getCurrencyForUri(sliceUri).symbol

            ListBuilder(context, sliceUri, 30 * 60 * 1000)
                .addRow {
                    it.setTitle("${symbol.toUpperCase()} Price = $$price")
                    it.addEndItem(getIconForUri(sliceUri), ListBuilder.SMALL_IMAGE)
                }
                .build()
        }
    }

    private fun loadPriceInformation(sliceUri: Uri) {
        val currency = getCurrencyForUri(sliceUri)

        priceService.getCryptocurrencyPrice(currency, "usd")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .addToCompositeDisposable(this)
            .doOnSuccess {
                when (currency) {
                    Currency.Bitcoin -> bitcoinPrice = it.price
                    Currency.Ether -> etherPrice = it.price
                    Currency.BitcoinCash -> bitcoinCashPrice = it.price
                }

                context.contentResolver.notifyChange(sliceUri, null)
            }
            .doOnError {
                // TODO: Render error state
            }
            .subscribe()
    }

    private fun getCurrencyForUri(sliceUri: Uri): Currency = when {
        sliceUri.path == "/prices/btc" -> Currency.Bitcoin
        sliceUri.path == "/prices/eth" -> Currency.Ether
        sliceUri.path == "/prices/bch" -> Currency.BitcoinCash
        else -> throw IllegalArgumentException("Unknown URI $sliceUri")
    }

    private fun getIconForUri(sliceUri: Uri): IconCompat = when {
        sliceUri.path == "/prices/btc" -> IconCompat.createWithResource(
            context,
            R.drawable.vector_bitcoin
        )
        sliceUri.path == "/prices/eth" -> IconCompat.createWithResource(
            context,
            R.drawable.vector_eth
        )
        sliceUri.path == "/prices/bch" -> IconCompat.createWithResource(
            context,
            R.drawable.vector_bitcoin_cash
        )
        else -> throw IllegalArgumentException("Unknown URI $sliceUri")
    }

    private fun createSliceShowingLoading(sliceUri: Uri): Slice {
        loadPriceInformation(sliceUri)
        // We’re waiting to load the time to work so indicate that on the slice by
        // setting the subtitle with the overloaded method and indicate true.
        return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
            .addRow {
                it.apply {
                    setTitle("Fetching ${getCurrencyForUri(sliceUri).symbol.toUpperCase()} price")
                    setSubtitle(null, true)
                    addEndItem(getIconForUri(sliceUri), ListBuilder.SMALL_IMAGE)
                }
            }
            .build()
    }

    /**
     * Slice has been pinned to external process. Subscribe to data source if necessary.
     */
    override fun onSlicePinned(sliceUri: Uri?) {
        sliceUri?.let { loadPriceInformation(it) }
    }

    /**
     * Unsubscribe from data source if necessary.
     */
    override fun onSliceUnpinned(sliceUri: Uri?) {
        compositeDisposable.clear()
    }
}
