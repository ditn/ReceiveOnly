package uk.co.adambennett.core.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import uk.co.adambennett.core.data.models.PriceDatum

interface Prices {

    @GET
    fun getCurrentPrice(
        @Url url: String,
        @Query("base") base: String,
        @Query("quote") quote: String
    ): Single<PriceDatum>
}