package uk.co.adambennett.receiveonly.slices.di

internal class SlicesInjector {

    private object Holder {
        val INSTANCE = SlicesInjector()
    }

    companion object {
        val instance: SlicesInjector by lazy { Holder.INSTANCE }
    }

    private var slicesComponent: SlicesComponent? = null

    fun getSlicesComponent(): SlicesComponent {
        if (slicesComponent == null)
            slicesComponent = DaggerSlicesComponent.builder()
                    .build()
        return slicesComponent!!
    }

}