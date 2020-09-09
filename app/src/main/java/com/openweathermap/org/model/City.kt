package com.openweathermap.org.model

import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("country")
    var country: String? = null
    @SerializedName("coord")
    var coord: Coord? = null
    @SerializedName("sunrise")
    var sunrise = 0
    @SerializedName("timezone")
    var timezone = 0
    @SerializedName("sunset")
    var sunset = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("id")
    var id = 0
    @SerializedName("population")
    var population = 0

}