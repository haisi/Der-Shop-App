package li.selman.dershop.tech.async

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ApplicationComponent::class)
object DispatcherModule {

    @Dispatcher(Type.DEFAULT)
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Dispatcher(Type.IO)
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Dispatcher(Type.MAIN)
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

/**
 * Allows you to use [CoroutineDispatcher] as a constructor argument while still specifying the concrete impl. in a compile-time safe way.
 *
 * Usage:
 * ```
 * class MyRepo
 *  @Inject constructor(
 *  @Dispatcher(Type.IO) private val dispatcher: CoroutineDispatcher, // Will use the IO dispatcher in prod; exchangeable for tests.
 *  ...
 *  ) {
 * ```
 *
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class Dispatcher(val value: Type)

enum class Type {
    DEFAULT, IO, MAIN
}
