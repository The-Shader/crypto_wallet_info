package com.fireblade.cryptowallet.di

import com.fireblade.cryptowallet.ui.HomeActivity
import com.fireblade.persistence.repository.di.PersistenceModule
//import com.fireblade.persistence.repository.di.PersistenceModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeActivityProvider {
    @ContributesAndroidInjector(modules = [PersistenceModule::class])
    fun bindHomeActivity(): HomeActivity
}