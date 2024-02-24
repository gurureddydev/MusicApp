package com.otpindiain.musicapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val repository: SongRepository) : ViewModel() {

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = _songs

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        viewModelScope.launch {
            try {
                val songsData = repository.getSongs()
                _songs.value = songsData

                // Log the retrieved songs data
                Log.d("SongViewModel", "Songs retrieved successfully: $songsData")
            } catch (e: Exception) {
                // Log an error if there's an exception
                Log.e("SongViewModel", "Error fetching songs", e)
            }
        }
    }
}
