package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse {
    @SerializedName("visibility")
    var visibility = 0
    @SerializedName("timezone")
    var timezone = 0
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("clouds")
    var clouds: Clouds? = null
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("dt")
    var dt = 0
    @SerializedName("coord")
    var coord: Coord? = null
    @SerializedName("weather")
    var weather: List<WeatherItem>? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("cod")
    var cod = 0
    @SerializedName("id")
    var id = 0
    @SerializedName("base")
    var base: String? = null
    @SerializedName("wind")
    var wind: Wind? = null

}