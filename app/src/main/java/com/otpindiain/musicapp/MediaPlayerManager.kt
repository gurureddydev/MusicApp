package com.otpindiain.musicapp

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MediaPlayerManager(private val context: Context) : LifecycleObserver {
    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex: Int = -1
    private var songs: List<Song> = emptyList()

    // LiveData for observing current playback time and duration
    private val _currentTime = MutableLiveData<Int>()
    private val _duration = MutableLiveData<Int>()
    val _isPlaying = MutableLiveData<Boolean>()

    init {
        initializeMediaPlayer()
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()

            mediaPlayer?.setAudioAttributes(audioAttributes)
        }

        // Set up a listener to update LiveData when playback position changes
        mediaPlayer?.setOnSeekCompleteListener {
            _currentTime.postValue(mediaPlayer?.currentPosition ?: 0)
        }

        // Set up a listener to update LiveData when media is prepared
        mediaPlayer?.setOnPreparedListener {
            _duration.postValue(mediaPlayer?.duration ?: 0)
        }

        // Set up a listener to update LiveData when playback state changes
        mediaPlayer?.setOnCompletionListener {
            _isPlaying.postValue(false)
        }

        // Initially set isPlaying to false
        _isPlaying.value = false
    }

    fun setSongs(songList: List<Song>) {
        songs = songList
    }

    fun play(index: Int) {
        if (index >= 0 && index < songs.size) {
            currentSongIndex = index
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(context, Uri.parse(songs[currentSongIndex].url))
            mediaPlayer?.prepare()
            mediaPlayer?.start()
            _isPlaying.postValue(true)
        }
    }

    fun play() {
        mediaPlayer?.start()
        _isPlaying.postValue(true)
    }

    fun pause() {
        mediaPlayer?.pause()
        _isPlaying.postValue(false)
    }

    fun next() {
        if (currentSongIndex < songs.size - 1) {
            play(currentSongIndex + 1)
        }
    }

    fun previous() {
        if (currentSongIndex > 0) {
            play(currentSongIndex - 1)
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // LiveData getters for observing current playback time, duration, and playback state
    fun getCurrentTime(): LiveData<Int> = _currentTime
    fun getDuration(): LiveData<Int> = _duration
    fun getIsPlaying(): LiveData<Boolean> = _isPlaying
}
