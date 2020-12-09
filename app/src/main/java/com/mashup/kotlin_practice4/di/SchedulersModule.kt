package com.mashup.kotlin_practice4.di

import com.mashup.kotlin_practice4.scheduler.BaseSchedulerProvider
import com.mashup.kotlin_practice4.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object SchedulersModule {

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}