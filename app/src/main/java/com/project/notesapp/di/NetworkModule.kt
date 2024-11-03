package com.project.notesapp.di

import com.project.notesapp.api.AuthInterceptor
import com.project.notesapp.api.NoteApi
import com.project.notesapp.api.UserApi
import com.project.notesapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit(client: OkHttpClient): Builder {
        return Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun getOkHttp(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun getUserApi(retrofitBuilder: Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun getNoteApi(retrofitBuilder: Builder): NoteApi {
        return retrofitBuilder.build().create(NoteApi::class.java)
    }


}