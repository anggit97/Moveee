package com.anggit97.navigation


/**
 * Created by Anggit Prayogo on 18,July,2021
 * GitHub : https://github.com/anggit97
 */
sealed class AuthEvent {
    class Authenticate(url: String) : AuthEvent()
}