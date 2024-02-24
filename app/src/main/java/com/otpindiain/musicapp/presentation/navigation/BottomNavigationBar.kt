package com.otpindiain.musicapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.otpindiain.musicapp.ForYouScreen
import com.otpindiain.musicapp.MediaPlayerManager
import com.otpindiain.musicapp.MusicPlayerScreen
import com.otpindiain.musicapp.SongViewModel
import com.otpindiain.musicapp.TopTracksScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(mediaPlayerManager:MediaPlayerManager) {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()
    val songViewModel: SongViewModel = viewModel()


    Scaffold(modifier = Modifier
        .fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .background(Color.Transparent) // Set the background color to transparent
            ) {
                bottomNavigationItems().forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(text = bottomNavigationItem.label)
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(bottomNavigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(3.dp)
                                    .clip(CircleShape)
                                    .border(
                                        10.dp,
                                        MaterialTheme.colorScheme.background,
                                        CircleShape
                                    )
                                    .background(Color.Transparent)
                            )
                        },
                    )
                }
            }
        }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.ForYou.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {

            composable(Screens.ForYou.route) {
                ForYouScreen(
                    navController,
                    songViewModel.songs.observeAsState().value ?: emptyList()
                )
            }
            composable(Screens.TopTrack.route) {
                TopTracksScreen(
                    navController, songViewModel.songs.observeAsState().value ?: emptyList()
                )
            }
            composable("${Screens.MusicPlayer}/{songId}") { backStackEntry ->
                val songId = backStackEntry.arguments?.getString("songId")?.toIntOrNull() ?: 0
                val song = songViewModel.songs.value?.find { it.id == songId }
                song?.let {
                    MusicPlayerScreen(
                        viewModel = songViewModel,
                        mediaPlayerManager = mediaPlayerManager,
                        songsData = it
                    )
                }
            }
        }
    }
}

@Composable
fun DotIndicator() {
    Box(
        modifier = Modifier
//            .background(Color.White) // You can customize the color of the dot
            .size(8.dp) // You can customize the size of the dot
    )
}
