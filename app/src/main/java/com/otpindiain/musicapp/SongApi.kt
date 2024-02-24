package com.otpindiain.musicapp

import retrofit2.http.GET

interface SongApi {
    @GET("items/songs")
    suspend fun getSongs(): SongsResponse
}
