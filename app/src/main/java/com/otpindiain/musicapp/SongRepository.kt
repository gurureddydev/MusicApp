package com.otpindiain.musicapp

import android.util.Log
import javax.inject.Inject

class SongRepository @Inject constructor(private val songApi: SongApi) {

    suspend fun getSongs(): List<Song> {
        try {
            val songsResponse = songApi.getSongs()
            val songs = songsResponse.data ?: emptyList()
            Log.d("SongRepository", "Songs from API: $songs")
            return songs
        } catch (e: Exception) {
            Log.e("SongRepository", "Error fetching songs from API", e)
            throw e // rethrow the exception to propagate it up
        }
    }
}


