package li.selman.dershop.tech

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object CrashlyticsTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        FirebaseCrashlytics.getInstance().log(message)

        if (t != null) {
            FirebaseCrashlytics.getInstance().recordException(t)
        }
    }
}
