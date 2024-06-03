package ua.nure.maksymburym.eyesana.app

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ua.nure.maksymburym.eyesana.BuildConfig
import ua.nure.maksymburym.eyesana.storage.Prefs
import ua.nure.maksymburym.eyesana.utils.LifecycleTracker
import ua.nure.maksymburym.eyesana.utils.setAppTheme
import javax.inject.Inject

@HiltAndroidApp
class App : BaseApp() {

    @Inject
    lateinit var storage: Prefs

    @Inject
    lateinit var appLifecycleTracker: LifecycleTracker

    override fun onCreate() {
        super.onCreate()
        initLogger()
        setTheme()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setTheme() {
        setAppTheme(storage.getAppTheme())
    }

    companion object {
        fun getApplication() = BaseApp.getApplication() as App
    }
}