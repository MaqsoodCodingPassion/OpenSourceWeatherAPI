package com.openweathermap.org

import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.Forecast5days3hoursResponse
import com.openweathermap.org.service.Service
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: Service) {

    /*
       Getting the details w.r.t city name
    */
    fun fetchCityWeatherData(cityName: String, apiKey: String): Observable<CurrentWeatherResponse> {
        return service.getCityWeatherDataService(cityName, apiKey)
    }

    /*
      Getting the current location details w.r.t current lat long
    */
    fun fetchCurrentWeatherDetails(lat: String, long: String, apiKey: String): Single<CurrentWeatherResponse> {
        return service.getCurrentWeatherDetailsService(lat, long, apiKey)
    }

    /*
      Getting the forecast details of 5 days with each 3 hours
    */
    fun fetchForecast5Days3Hours(cityName: String, apiKey: String): Single<Forecast5days3hoursResponse> {
        return service.getForecast5Days3HoursService(cityName, apiKey)
    }
}
