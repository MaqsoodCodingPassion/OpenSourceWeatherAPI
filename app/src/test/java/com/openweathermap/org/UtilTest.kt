package com.openweathermap.org

import java.io.File

fun getJson(path: String): String {
    val file = File("src/test/resources/$path")
    return String(file.readBytes())
}