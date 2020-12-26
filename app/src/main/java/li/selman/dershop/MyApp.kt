package li.selman.dershop

import android.app.Application
import timber.log.Timber

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
