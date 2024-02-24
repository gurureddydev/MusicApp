package com.otpindiain.musicapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MusicService : LifecycleService() {
    companion object {
        const val ACTION_PLAY_PAUSE = "action_play_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREVIOUS = "action_previous"
    }

    private lateinit var mediaPlayerManager: MediaPlayerManager
    private val binder = MusicServiceBinder()

    inner class MusicServiceBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()

        // Fetch the list of songs from your data source
        val songList: List<Song> = fetchSongsFromDataSource()

        // Initialize the MediaPlayerManager and set the fetched songs
        mediaPlayerManager = MediaPlayerManager(this)
        mediaPlayerManager.setSongs(songList)

        lifecycle.addObserver(mediaPlayerManager)

        mediaPlayerManager.getIsPlaying().observe(this, Observer { isPlaying ->
            if (isPlaying) {
                // Start playback
                mediaPlayerManager.play()
            } else {
                // Pause playback
                mediaPlayerManager.pause()
            }
        })
    }
    private fun fetchSongsFromDataSource(): List<Song> {
        val json = """
        // Your JSON data here
    """.trimIndent()

        val songListType = object : TypeToken<List<Song>>() {}.type
        return Gson().fromJson(json, songListType)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when (action) {
                ACTION_PLAY_PAUSE -> {
                    // Toggle between play and pause
                    if (mediaPlayerManager.getIsPlaying().value == true) {
                        mediaPlayerManager.pause()
                    } else {
                        mediaPlayerManager.play()
                    }
                }
                ACTION_NEXT -> {
                    mediaPlayerManager.next()
                }
                ACTION_PREVIOUS -> {
                    mediaPlayerManager.previous()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerManager.releaseMediaPlayer()
    }

    // Start the service as a foreground service
    @SuppressLint("ForegroundServiceType")
    fun startForegroundService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification: Notification = NotificationCompat.Builder(this, "channelId")
            .setContentTitle("Music Player")
            .setContentText("Now Playing")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }
}
