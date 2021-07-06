package com.anggit97.data.db.internal.converter

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * Created by Anggit Prayogo on 06,July,2021
 * GitHub : https://github.com/anggit97
 */
internal object FavouriteMovieTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromString(string: String?): List<String>? {
        if (string == null) {
            return null
        }
        return Json.decodeFromString(string)
    }

    @TypeConverter
    @JvmStatic
    fun toString(value: List<String>?): String {
        return Json.encodeToString(value.orEmpty())
    }
}