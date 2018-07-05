package uk.co.adambennett.core.data.models

sealed class Currency(val symbol: String) {

    object Bitcoin : Currency("btc")
    object Ether : Currency("eth")
    object BitcoinCash : Currency("btc")
}
