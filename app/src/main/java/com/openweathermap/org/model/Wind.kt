package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class Wind {
    @SerializedName("deg")
    var deg = 0
    @SerializedName("speed")
    var speed = 0.0

}