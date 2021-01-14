package dev.filipebezerra.app.propertiesfrommars

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MARS_BASE_API_URL
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object ServiceLocator {

    private val okHttpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
            .readTimeout(30, SECONDS)
            .callTimeout(60, SECONDS)
    }

    private val moshiBuilder: Moshi.Builder by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory())
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MARS_BASE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
            .client(okHttpClientBuilder.build())
            .build()
    }

    @Volatile
    private var marsApiService: MarsApiService? = null

    fun proviceMarsApiService(): MarsApiService = marsApiService ?: synchronized(this) {
        retrofit.create(MarsApiService::class.java)
    }
}