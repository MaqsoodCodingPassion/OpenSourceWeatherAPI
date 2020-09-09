package com.openweathermap.org

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.Forecast5days3hoursResponse
import com.openweathermap.org.service.Service
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherViewModelTest {

    lateinit var viewModel: WeatherViewModel

    private lateinit var weatherResponse: CurrentWeatherResponse

    private lateinit var forecast5days3hoursResponse: Forecast5days3hoursResponse

    @Mock
    lateinit var mockRepository: WeatherRepository

    @Mock
    private lateinit var mockService: Service

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherViewModel(mockRepository)

        val currentWeatherResponse = getJson("json/weather_response.json")
        val forecast5days3hoursResponse = getJson("json/forecast_5days_3hours_response.json")
        val turnsType1 = object : TypeToken<CurrentWeatherResponse>() {}.type
        this.weatherResponse =
            Gson().fromJson<CurrentWeatherResponse>(currentWeatherResponse, turnsType1)
        Mockito.`when`(mockService.getCurrentWeatherDetailsService("3222.222", "223.2", "2332e23333"))
            .thenReturn(Single.just(this.weatherResponse))


        val turnsType2 = object : TypeToken<Forecast5days3hoursResponse>() {}.type
        this.forecast5days3hoursResponse =
            Gson().fromJson<Forecast5days3hoursResponse>(forecast5days3hoursResponse, turnsType2)
        Mockito.`when`(mockService.getForecast5Days3HoursService("Bengaluru", "2332e23333"))
            .thenReturn(Single.just(this.forecast5days3hoursResponse))

        Mockito.`when`(mockService.getCityWeatherDataService("Bengaluru", "2332e23333"))
            .thenReturn(Observable.just(this.weatherResponse))
    }

   /*
    Testing CurrentLocation weather data
   */
    @Test
    fun `Test fetchCurrentLocationDetailsObservableLiveData`() {
        Mockito.`when`(
            mockRepository.fetchCurrentWeatherDetails(
                "324244",
                "44224423",
                "rffew23232"
            )
        ).thenReturn(
            Single.just(weatherResponse)
        )
        viewModel.fetchCurrentLocationDetails("324244", "44224423", "rffew23232")
        Mockito.verify(mockRepository)
            .fetchCurrentWeatherDetails("324244", "44224423", "rffew23232")
    }

    /*
     Testing 5 days, 3 hours each weather data
    */
    @Test
    fun `Test fetchForeCast5Days3HoursObservableLiveData`() {
        Mockito.`when`(
            mockRepository.fetchForecast5Days3Hours(
                "Bengaluru",
                "44224423"
            )
        ).thenReturn(
            Single.just(forecast5days3hoursResponse)
        )
        viewModel.fetchForeCast5Days3HoursData("Bengaluru", "44224423")
        Mockito.verify(mockRepository).fetchForecast5Days3Hours("Bengaluru", "44224423")
    }

    /*
      Testing multiple cities weather data
     */
    @Test
    fun `Test fetchMultipleCitiesWeatherObservableLiveData`() {
        Mockito.`when`(
            mockRepository.fetchCityWeatherData(
                "Bengaluru",
                "44224423"
            )
        ).thenReturn(
            Observable.just(weatherResponse)
        )

        var citiesList = ArrayList<String>()
        citiesList.add("Bengaluru")
        citiesList.add("Mysore")
        citiesList.add("Dublin")
        citiesList.add("London")

        viewModel.fetchMultipleCitiesWeatherData(citiesList, "44224423")
        Mockito.verify(mockRepository).fetchCityWeatherData("Bengaluru", "44224423")
    }
}