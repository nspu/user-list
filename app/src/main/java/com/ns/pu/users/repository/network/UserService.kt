package com.ns.pu.users.repository.network

import com.ns.pu.users.repository.dto.Users
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val interceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}
private val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://randomuser.me")
    .addConverterFactory(GsonConverterFactory.create())
    .client(httpClient)
    .build()
val serviceRetrofit: UserService = retrofit.create(UserService::class.java)


interface UserService {

    @GET("/api/1.0/?seed=lydia")
    suspend fun users(
        @Query("results") usersByPage: Int,
        @Query("page") pageNumber: Int,
    ): Response<Users>
}