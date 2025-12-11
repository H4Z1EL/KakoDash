package com.example.kakodash.network
//revision completa de api service
import com.example.kakodash.model.Item
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("items")
    suspend fun getItems(): Response<List<Item>>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: Int): Response<Item>

    @POST("items")
    suspend fun createItem(@Body item: Item): Response<Item>

    @PUT("items/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body item: Item): Response<Item>

    @DELETE("items/{id}")
    suspend fun deleteItem(@Path("id") id: Int): Response<Unit>
}