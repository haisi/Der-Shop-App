package li.selman.dershop.tech.network

import li.selman.dershop.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NyTimeApiAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val token = BuildConfig.NY_TIMES_API_KEY
        val url = original.url.newBuilder().addQueryParameter("api-key", token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
