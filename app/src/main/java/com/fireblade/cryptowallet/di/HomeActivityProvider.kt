package com.fireblade.cryptowallet.di

import com.fireblade.cryptowallet.ui.HomeActivity
import com.fireblade.repository.repository.di.RepositoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface HomeActivityProvider {
    @ContributesAndroidInjector(modules = [RepositoryModule::class])
    fun bindHomeActivity(): HomeActivity
}