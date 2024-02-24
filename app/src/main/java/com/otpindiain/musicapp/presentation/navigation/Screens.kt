package com.otpindiain.musicapp.presentation.navigation

sealed class Screens(val route:String){
    object ForYou : Screens("for_you")
    object TopTrack : Screens("Top_track")
    object MusicPlayer : Screens("music_player")
}