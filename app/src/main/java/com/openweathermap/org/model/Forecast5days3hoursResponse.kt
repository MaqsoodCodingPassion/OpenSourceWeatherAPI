package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class Forecast5days3hoursResponse {
    @SerializedName("city")
    var city: City? = null
    @SerializedName("cnt")
    var cnt = 0
    @SerializedName("cod")
    var cod: String? = null
    @SerializedName("message")
    var message = 0
    @SerializedName("list")
    var list: List<ListItem>? = null

}