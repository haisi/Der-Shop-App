package li.selman.dershop.ui.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Use this for communicating on-off events between ViewModel and View, e.g. to show a SnackBar.
 * If you used a regular [MutableLiveData], every time the View subscribes to the observable, the last event will be re-send.
 * Thus, you would receive duplicate events.
 *
 * @see <a href="https://proandroiddev.com/navigation-events-in-mvvm-on-android-via-livedata-5c88ef48ee83">this blog for more info</a>
 */
open class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Util function for Void implementations.
     */
    fun call() {
        value = null
    }
}
