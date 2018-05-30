package uk.co.adambennett.core.data.services

import io.reactivex.Single
import retrofit2.Retrofit
import uk.co.adambennett.core.data.api.BASE_API
import uk.co.adambennett.core.data.api.PATH_SINGLE_PRICE
import uk.co.adambennett.core.data.api.Prices
import uk.co.adambennett.core.data.models.Currency
import uk.co.adambennett.core.data.models.PriceDatum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceService @Inject constructor(retrofit: Retrofit) {

    private val service: Prices = retrofit.create(Prices::class.java)

    /**
     * Returns the current price for a given cryptocurrency.
     *
     * @param base  The base cryptocurrency for which to gather prices, eg "eth", "btc" or "bcc"
     * @param quote The fiat currency in which to return the prices, eg "usd"
     *
     * @return A [Single] containing a [PriceDatum] object, which represents the price in the
     * current moment.
     */
    fun getCryptocurrencyPrice(
            base: Currency,
            quote: String
    ): Single<PriceDatum> = service.getCurrentPrice(BASE_API + PATH_SINGLE_PRICE, base.symbol, quote)

}