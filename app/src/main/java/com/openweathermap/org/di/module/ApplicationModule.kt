package com.openweathermap.org.di.module

import com.openweathermap.org.di.BaseUrl
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "http://api.openweathermap.org/data/2.5/"
    }
}