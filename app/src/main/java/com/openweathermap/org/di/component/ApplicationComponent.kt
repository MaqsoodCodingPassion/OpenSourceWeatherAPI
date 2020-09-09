package com.openweathermap.org.di.component

import com.openweathermap.org.WeatherApplication
import com.openweathermap.org.di.module.ActivityBuilder
import com.openweathermap.org.di.module.ApplicationModule
import com.openweathermap.org.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (NetworkModule::class),
        (ApplicationModule::class),
        (ActivityBuilder::class)]
)

interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: WeatherApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
}
