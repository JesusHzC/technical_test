package com.example.technicaltest.di

import com.example.technicaltest.BuildConfig
import com.example.technicaltest.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private const val API_KEY: String = BuildConfig.API_MOVIE_KEY

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().url
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(newRequest).build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun providerMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpClient())
            .build()
            .create(MovieApi::class.java)
    }

}