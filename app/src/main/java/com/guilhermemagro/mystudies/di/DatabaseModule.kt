package com.guilhermemagro.mystudies.di

import android.content.Context
import androidx.room.Room
import com.guilhermemagro.mystudies.data.dao.StudyItemDao
import com.guilhermemagro.mystudies.data.database.AppDatabase
import com.guilhermemagro.mystudies.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: AppDatabase): StudyItemDao {
        return database.studyItemDao()
    }
}
