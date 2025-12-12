package com.example.kakodash.network

import com.example.kakodash.model.Profile
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface ApiService {

    @GET("profile")
    suspend fun getProfile(): Profile

    @PUT("profile")
    suspend fun updateProfile(@Body profile: Profile): Profile

    @DELETE("profile")
    suspend fun deleteProfile()
}
