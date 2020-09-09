package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class Sys {
    @SerializedName("country")
    var country: String? = null
    @SerializedName("sunrise")
    var sunrise = 0
    @SerializedName("sunset")
    var sunset = 0
    @SerializedName("id")
    var id = 0
    @SerializedName("type")
    var type = 0

}