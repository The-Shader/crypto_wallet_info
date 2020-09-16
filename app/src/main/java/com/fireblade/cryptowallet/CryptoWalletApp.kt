package com.fireblade.cryptowallet

import android.app.Application
import com.fireblade.cryptowallet.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CryptoWalletApp : DaggerApplication() {

//    @Inject
//    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    //override fun androidInjector(): DispatchingAndroidInjector<Any> = androidInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .factory()
            .create(this)

//    override fun onCreate() {
//        super.onCreate()
//
//        DaggerAppComponent
//            .factory()
//            .create(this)
//            .inject(this)
//    }
}