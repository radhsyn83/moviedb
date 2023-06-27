package com.radhsyn83.themoviedb.common

import com.radhsyn83.themoviedb.BuildConfig.API_KEY
import com.radhsyn83.themoviedb.BuildConfig.BASE_URL
import com.radhsyn83.themoviedb.data.remote.MovieDBApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkInstance {

    fun api(): MovieDBApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBApi::class.java)
    }

    private fun getInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        okHttp.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Authorization", API_KEY)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        return okHttp.build()
    }
}