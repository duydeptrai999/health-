package com.studyai.health.di

import android.content.Context
import com.studyai.health.data.dao.HealthRecordDao
import com.studyai.health.data.database.AppDatabase
import com.studyai.health.data.repositories.HealthRecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides dependencies for the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides the application database.
     */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    /**
     * Provides the HealthRecordDao.
     */
    @Singleton
    @Provides
    fun provideHealthRecordDao(appDatabase: AppDatabase): HealthRecordDao {
        return appDatabase.healthRecordDao()
    }
    
    /**
     * Provides the HealthRecordRepository.
     */
    @Singleton
    @Provides
    fun provideHealthRecordRepository(healthRecordDao: HealthRecordDao): HealthRecordRepository {
        return HealthRecordRepository(healthRecordDao)
    }
}