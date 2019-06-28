package by.next.way.retrofit.kotlin

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("/get")
    suspend fun get(): Response<String>

    @POST("/post")
    suspend fun post(@Body raw: String): Response<String>
}