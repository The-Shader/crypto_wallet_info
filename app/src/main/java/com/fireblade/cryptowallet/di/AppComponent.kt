package com.fireblade.cryptowallet.di

import android.app.Application
import com.fireblade.cryptowallet.CryptoWalletApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ApplicationModule::class, HomeActivityProvider::class]
)
interface AppComponent : AndroidInjector<CryptoWalletApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    override fun inject(app: CryptoWalletApp)
}