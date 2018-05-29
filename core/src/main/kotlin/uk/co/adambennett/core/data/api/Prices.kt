package uk.co.adambennett.core.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.adambennett.core.data.models.PriceDatum

interface Prices {

    @GET(PATH_SINGLE_PRICE)
    fun getCurrentPrice(
            @Query("base") base: String,
            @Query("quote") quote: String
    ): Single<PriceDatum>

}