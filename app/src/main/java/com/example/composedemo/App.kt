package com.example.composedemo

import android.app.Application
import com.example.composedemo.features_note.presentation.utils.PrefsUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var prefsUtils: PrefsUtils

}