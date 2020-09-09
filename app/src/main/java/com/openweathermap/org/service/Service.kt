package com.openweathermap.org.service

import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.Forecast5days3hoursResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    /*
      Getting the details w.r.t city name
    */
    @GET("weather")
    fun getCityWeatherDataService(@Query("q") lat: String, @Query("appid") appid: String)
            : Observable<CurrentWeatherResponse>

    /*
       Getting the current city name details w.r.t current lat long
     */
    @GET("weather")
    fun getCurrentWeatherDetailsService(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String)
            : Single<CurrentWeatherResponse>

    /*
       Getting the forecast details of 5 days with each 3 hours
     */
    @GET("forecast")
    fun getForecast5Days3HoursService(@Query("q") cityName: String, @Query("appid") appid: String)
            : Single<Forecast5days3hoursResponse>
}