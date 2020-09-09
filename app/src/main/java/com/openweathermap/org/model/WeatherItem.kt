package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class WeatherItem {
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("main")
    var main: String? = null
    @SerializedName("id")
    var id = 0

}