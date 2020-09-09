package com.openweathermap.org.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openweathermap.org.WeatherRepository
import com.openweathermap.org.WeatherViewModel
import javax.inject.Inject

class ViewModelFactory
    @Inject constructor(private val repository: WeatherRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WeatherViewModel::class.java) -> WeatherViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}