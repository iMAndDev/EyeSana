package ua.nure.maksymburym.eyesana.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import javax.inject.Inject

class LifecycleTracker @Inject constructor() : Application.ActivityLifecycleCallbacks {

    private val activities: MutableMap<Class<out Activity>, Lifecycle.Event> = mutableMapOf()
    var activityStartedCount = 0
        private set

    val isAppInForeground: Boolean
        get() {
            return activities.isNotEmpty() &&
                    activities.containsValue(Lifecycle.Event.ON_RESUME) ||
                    activities.containsValue(Lifecycle.Event.ON_PAUSE)
        }

    fun <T : Activity> isActivityForeground(activity: Class<T>): Boolean {
        return activities[activity] != null &&
                activities[activity] == Lifecycle.Event.ON_RESUME ||
                activities[activity] == Lifecycle.Event.ON_PAUSE
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activities[activity::class.java] = Lifecycle.Event.ON_CREATE
    }

    override fun onActivityStarted(activity: Activity) {
        activities[activity::class.java] = Lifecycle.Event.ON_START
        activityStartedCount += 1
    }

    override fun onActivityResumed(activity: Activity) {
        activities[activity::class.java] = Lifecycle.Event.ON_RESUME
    }

    override fun onActivityPaused(activity: Activity) {
        activities[activity::class.java] = Lifecycle.Event.ON_PAUSE
    }

    override fun onActivityStopped(activity: Activity) {
        activities[activity::class.java] = Lifecycle.Event.ON_STOP
        activityStartedCount -= 1
    }

    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity::class.java)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { /* do nothing */ }
}
