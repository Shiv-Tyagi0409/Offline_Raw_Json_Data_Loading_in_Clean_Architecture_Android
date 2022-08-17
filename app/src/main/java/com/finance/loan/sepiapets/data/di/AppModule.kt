package com.finance.loan.sepiapets.data.di

import android.content.Context
import com.finance.loan.sepiapets.App
import com.finance.loan.sepiapets.data.repository.MainRepositoryImpl
import com.finance.loan.sepiapets.domain.MainRepository1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationClass(@ApplicationContext app: Context): App =
        app as App

    @Singleton
    @Provides
    fun provideMainRepository(app: App) : MainRepository1 {
        return MainRepositoryImpl(app)
    }
}