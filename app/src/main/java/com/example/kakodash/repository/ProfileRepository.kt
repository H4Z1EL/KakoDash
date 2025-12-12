package com.example.kakodash.repository

import com.example.kakodash.model.Profile
import com.example.kakodash.network.ApiService

class ProfileRepository(private val api: ApiService) {

    suspend fun getProfile(): Profile =
        api.getProfile()

    suspend fun updateProfile(profile: Profile): Profile =
        api.updateProfile(profile)

    suspend fun deleteProfile() =
        api.deleteProfile()
}
