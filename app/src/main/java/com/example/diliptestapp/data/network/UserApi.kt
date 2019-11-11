package com.example.diliptestapp.data.network

import com.example.diliptestapp.data.db.entities.User
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface MyUserApi {

    @GET("users")
    suspend fun getUserResponse(): Response<List<User>>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyUserApi {

            val okkHttpclient = OkHttpClient.Builder()
                .connectTimeout(420, TimeUnit.SECONDS)
                .readTimeout(420, TimeUnit.SECONDS)
                .writeTimeout(420, TimeUnit.SECONDS)
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyUserApi::class.java)
        }
    }

}

