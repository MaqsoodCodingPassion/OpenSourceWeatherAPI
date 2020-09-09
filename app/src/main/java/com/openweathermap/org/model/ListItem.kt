package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class ListItem {
    @SerializedName("dt")
    var dt = 0
    @SerializedName("dt_txt")
    var dtTxt: String? = null
    @SerializedName("weather")
    var weather: List<WeatherItem>? = null
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("clouds")
    var clouds: Clouds? = null
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("wind")
    var wind: Wind? = null

}