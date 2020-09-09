package com.synchronoss.currentweather.model

import com.openweathermap.org.model.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrentWeatherResponseTest {

    private var currentWeatherResponse = CurrentWeatherResponse()

    @Before
    fun setUp() {
        currentWeatherResponse.wind?.deg = 22
        currentWeatherResponse.name = "khan"

        var main = Main()
        main.humidity = 22
        main.pressure = 222
        main.temp = 22.3
        main.tempMax = 11.22
        main.tempMin = 11.1

        var wind = Wind()
        wind?.deg = 32332
        wind?.speed = 111.232
        currentWeatherResponse.wind = wind

        var clouds = Clouds()
        clouds?.all = 23
        currentWeatherResponse.clouds = clouds

        currentWeatherResponse.main = main
        var weatherItem = WeatherItem()
        weatherItem.icon = "34d"
        weatherItem.id = 223
        weatherItem.main  = "cloud"
        weatherItem.description = "cloud"

        var weatherList = ArrayList<WeatherItem>()
        weatherList?.add(weatherItem)

        currentWeatherResponse.weather = weatherList
        currentWeatherResponse.visibility = 3223
        currentWeatherResponse.base = "332"
        currentWeatherResponse.wind?.deg = 32332
        currentWeatherResponse.wind?.speed = 111.232

        var sysItem = Sys()
        sysItem?.sunrise =22
        sysItem?.country = "India"
        sysItem?.type = 2
        sysItem?.id = 22

        var coord = Coord()
        coord?.lat = 22.22
        coord?.lon = 22.22
        currentWeatherResponse.coord = coord

        currentWeatherResponse.sys = sysItem
        currentWeatherResponse.sys?.country = "India"
        currentWeatherResponse.sys?.type = 2
        currentWeatherResponse.sys?.id = 22

        currentWeatherResponse.timezone = 222
    }
    
    @After
    fun tearDown() {

    }

    @Test
    fun getVisibility() {
        Assert.assertNotNull(currentWeatherResponse.visibility)
    }

    @Test
    fun getTimezone() {
        Assert.assertNotNull(currentWeatherResponse.timezone)
    }

    @Test
    fun getMain() {
        Assert.assertNotNull(currentWeatherResponse.main)
    }

    @Test
    fun getClouds() {
        Assert.assertNotNull(currentWeatherResponse.clouds)
    }

    @Test
    fun getSys() {
        Assert.assertNotNull(currentWeatherResponse.sys)
    }

    @Test
    fun getDt() {
        Assert.assertNotNull(currentWeatherResponse.dt)
    }


    @Test
    fun getCoord() {
        Assert.assertNotNull(currentWeatherResponse.coord)
    }

    @Test
    fun getWeatherListSize() {
        Assert.assertTrue(currentWeatherResponse.weather?.size!! > 0)
    }

    @Test
    fun getName() {
        Assert.assertNotNull(currentWeatherResponse.name)
    }

    @Test
    fun getCod() {
        Assert.assertNotNull(currentWeatherResponse.cod)
    }

    @Test
    fun getId() {
        Assert.assertNotNull(currentWeatherResponse.id)
    }

    @Test
    fun getBase() {
        Assert.assertNotNull(currentWeatherResponse.base)
    }

    @Test
    fun getWind() {
        Assert.assertNotNull(currentWeatherResponse.wind)
    }
}