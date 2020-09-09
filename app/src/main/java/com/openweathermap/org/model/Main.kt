package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class Main {
    @SerializedName("temp")
    var temp = 0.0
    @SerializedName("temp_min")
    var tempMin = 0.0
    @SerializedName("humidity")
    var humidity = 0
    @SerializedName("pressure")
    var pressure = 0
    @SerializedName("feels_like")
    var feelsLike = 0.0
    @SerializedName("temp_max")
    var tempMax = 0.0

}