package com.otpindiain.musicapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@SuppressLint("RememberReturnType")
@Composable
fun MusicPlayerScreen(
    viewModel: SongViewModel,
    mediaPlayerManager: MediaPlayerManager,
    songsData: Song
) {
    var isPlaying by remember { mutableStateOf(false) }
    val currentTime by mediaPlayerManager.getCurrentTime().observeAsState(0)
    val duration by mediaPlayerManager.getDuration().observeAsState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = songsData.name,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White
                    )
                )
                Text(
                    text = songsData.artist,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://cms.samespace.com/assets/${songsData.cover}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Music Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(10))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatTime(currentTime),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White
                    )
                )
                Text(
                    text = formatTime(duration),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { mediaPlayerManager.previous() },
                    content = {
                        Icon(
                            Icons.Default.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Skip Previous"
                        )
                    }
                )
                IconButton(
                    onClick = {
                        if (isPlaying) mediaPlayerManager.pause() else mediaPlayerManager.play()
                        isPlaying = !isPlaying
                    },
                    content = {
                        Icon(
                            if (isPlaying) Icons.Default.Email else Icons.Default.PlayArrow,
                            tint = Color.White,
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                    }
                )
                IconButton(
                    onClick = { mediaPlayerManager.next() },
                    content = {
                        Icon(
                            Icons.Default.ArrowForward,
                            tint = Color.White,
                            contentDescription = "Skip Next"
                        )
                    }
                )
            }
        }
    }
}

// Your formatTime function remains unchanged
fun formatTime(millis: Int): String {
    val minutes = millis / 1000 / 60
    val seconds = (millis / 1000) % 60
    return "%02d:%02d".format(minutes, seconds)
}
