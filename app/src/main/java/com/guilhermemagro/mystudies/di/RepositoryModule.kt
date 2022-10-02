package com.guilhermemagro.mystudies.di

import com.guilhermemagro.mystudies.data.repositories.ItemRepository
import com.guilhermemagro.mystudies.data.repositories.ItemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository
}
