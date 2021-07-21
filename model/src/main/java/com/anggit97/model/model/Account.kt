package com.anggit97.model.model


/**
 * Created by Anggit Prayogo on 21,July,2021
 * GitHub : https://github.com/anggit97
 */
data class Account(
    val id: Int,
    val iso639: String,
    val iso3166: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String,
    val gravatarHash: String,
    val tmdbAvatarPath: String
) {

    fun getGravatarImageUrl() = "https://www.gravatar.com/avatar/$gravatarHash"
}