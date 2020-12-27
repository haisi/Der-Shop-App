package li.selman.dershop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import li.selman.dershop.tech.CrashlyticsTree
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree)
        }

        super.onCreate()
    }
}
