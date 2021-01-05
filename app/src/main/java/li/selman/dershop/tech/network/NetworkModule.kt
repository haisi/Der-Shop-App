package li.selman.dershop.tech.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import li.selman.dershop.business.popular.MostPopularApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    const val BASE_ENDPOINT = "https://api.nytimes.com/svc/"

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // To read field names using reflection
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(NyTimeApiAuthInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, mosh: Moshi) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(mosh))
        .addConverterFactory(CustomPathTypeConverterFactory())
        .baseUrl(BASE_ENDPOINT)
        .build()

    @Provides
    @Singleton
    fun mostPopularAPI(builder: Retrofit): MostPopularApi = builder.create(MostPopularApi::class.java)
}
