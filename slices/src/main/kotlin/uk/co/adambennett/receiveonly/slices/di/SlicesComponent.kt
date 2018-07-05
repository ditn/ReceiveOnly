package uk.co.adambennett.receiveonly.slices.di

import dagger.Component
import uk.co.adambennett.core.di.ApiModule
import uk.co.adambennett.receiveonly.slices.CryptoPriceSliceProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface SlicesComponent {

    fun inject(target: CryptoPriceSliceProvider)
}
