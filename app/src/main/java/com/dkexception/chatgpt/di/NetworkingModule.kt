package com.dkexception.chatgpt.di

import com.dkexception.chatgpt.BuildConfig
import com.dkexception.chatgpt.data.remote.OpenAIAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(2, TimeUnit.MINUTES)
        .connectTimeout(2, TimeUnit.MINUTES)
        .callTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        ).build()

    @Provides
    @Singleton
    fun provideOpenAIAPI(
        okHttpClient: OkHttpClient
    ): OpenAIAPI = Retrofit.Builder()
        .baseUrl(BuildConfig.openAIBackendBaseURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(OpenAIAPI::class.java)
}
