package com.otpindiain.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.otpindiain.musicapp.presentation.navigation.BottomNavigationBar
import com.otpindiain.musicapp.presentation.navigation.Screens
import com.otpindiain.musicapp.ui.theme.MusicAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mediaPlayerManager = MediaPlayerManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    BottomNavigationBar(mediaPlayerManager)
                }
            }
        }
    }
}


@Composable
fun TopTracksScreen(navController: NavController, songs: List<Song>) {
    LazyColumn {
        songs.forEach { item ->
            item {
                // Pass the individual item to the ProfileCard composable
                TrackItem(navController, songs = item)
            }
        }
    }
}

@Composable
fun ForYouScreen(navController: NavController, songs: List<Song>) {

    LazyColumn {
        songs.forEach { item ->
            item {
                // Pass the individual item to the ProfileCard composable
                TrackItem(navController, songs = item)
            }
        }
    }
}


@Composable
fun TrackItem(navController: NavController, songs: Song) {
    Row(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .clickable {
            navController.navigate("${Screens.MusicPlayer}/${songs.id}")
        }
        .padding(16.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://cms.samespace.com/assets/${songs.cover}").crossfade(true).build(),
            contentDescription = "Music Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(0.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = songs.name ?: "Unknown Name",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = songs.artist ?: "Unknown Artist",
                modifier = Modifier.padding(all = 4.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}


