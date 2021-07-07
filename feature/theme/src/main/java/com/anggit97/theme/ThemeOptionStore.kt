package com.anggit97.theme


/**
 * Created by Anggit Prayogo on 07,July,2021
 * GitHub : https://github.com/anggit97
 */
interface ThemeOptionStore {

    fun save(option: String)

    fun restore(): String
}