package ua.nure.maksymburym.eyesana.app

import android.app.Application

abstract class BaseApp : Application() {
    companion object {
        private lateinit var application: BaseApp
        fun getApplication() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}