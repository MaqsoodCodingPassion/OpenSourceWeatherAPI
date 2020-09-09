package com.openweathermap.org

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openweathermap.org.model.CurrentWeatherResponse
import com.openweathermap.org.model.Forecast5days3hoursResponse
import com.openweathermap.org.service.Service
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ServiceAPIsTest {

    private lateinit var currentWeatherResponse: CurrentWeatherResponse

    private lateinit var forecast5days3hoursResponse: Forecast5days3hoursResponse

    @Mock
    private lateinit var service: Service

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val currentWeatherResponse = getJson("json/weather_response.json")
        val forecast5days3hoursResponse = getJson("json/forecast_5days_3hours_response.json")
        val turnsType1 = object : TypeToken<CurrentWeatherResponse>() {}.type
        this.currentWeatherResponse = Gson().fromJson<CurrentWeatherResponse>(currentWeatherResponse, turnsType1)
        Mockito.`when`(service.getCurrentWeatherDetailsService("3222.222", "223.2", "2332e23333"))
            .thenReturn(Single.just(this.currentWeatherResponse))

        val turnsType2 = object : TypeToken<Forecast5days3hoursResponse>() {}.type
        this.forecast5days3hoursResponse = Gson().fromJson<Forecast5days3hoursResponse>(forecast5days3hoursResponse, turnsType2)
        Mockito.`when`(service.getForecast5Days3HoursService("Bengaluru", "2332e23333"))
            .thenReturn(Single.just(this.forecast5days3hoursResponse))

        Mockito.`when`(service.getCityWeatherDataService("Bengaluru", "2332e23333"))
            .thenReturn(Observable.just(this.currentWeatherResponse))

    }

    @Test
    fun `Test getWeatherDetails API should not null`() {
        assertNotNull(service.getCurrentWeatherDetailsService("3222.222", "223.2", "2332e23333"))
    }

    @Test
    fun `Test getCurrentWeatherDetails data items are not null`() {
        assertNotNull(service.getCurrentWeatherDetailsService("3222.222", "223.2", "2332e23333")
            .test()
            .assertComplete()
            .assertValue {
                it.base != null
                it.name != null
                it.clouds!!.all != null
                it.visibility != null
                it.sys != null
                it.weather != null
                it.wind != null
                it.id != null
            })
    }

    @Test
    fun `Test Forecast5days3hoursResponse data items are not null`() {
        assertNotNull(service.getForecast5Days3HoursService("Bengaluru","2332e23333")
            .test()
            .assertComplete()
            .assertValue {
                it.city != null
                it.cnt != null
                it.list != null
                it.list!!.isNotEmpty()
                it.list!!.get(0).clouds != null
                it.list!!.get(0).dt != null
                it.list!!.get(0).dtTxt != null
                it.list!!.get(0).main != null
                it.list!!.get(0).weather != null
                it.list!!.get(0).wind != null
            })
    }

    @Test
    fun `Test getCityWeatherDataService API should not null`() {
        assertNotNull(service.getCityWeatherDataService("Bengaluru", "2332e23333"))
    }

    @Test
    fun `Test getCityWeatherDataService data items are not null`() {
        assertNotNull(service.getCityWeatherDataService("Bengaluru", "2332e23333")
            .test()
            .assertComplete()
            .assertValue {
                it.base != null
                it.name != null
                it.clouds!!.all != null
                it.visibility != null
                it.sys != null
                it.weather != null
                it.wind != null
                it.id != null
            })
    }
}