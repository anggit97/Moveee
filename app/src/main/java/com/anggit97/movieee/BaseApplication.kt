package com.anggit97.movieee

import android.app.Application
import com.anggit97.theme.ThemeOptionManager
import com.google.firebase.FirebaseApp
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 03,July,2021
 * GitHub : https://github.com/anggit97
 */
@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var themeOptionManager: ThemeOptionManager

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        themeOptionManager.initialize()
    }
}