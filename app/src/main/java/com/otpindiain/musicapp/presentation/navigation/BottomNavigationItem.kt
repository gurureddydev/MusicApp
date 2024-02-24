package com.otpindiain.musicapp.presentation.navigation

data class BottomNavigationItem(
    val label: String, val route: String = ""
)

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "For You", route = Screens.ForYou.route
        ), BottomNavigationItem(
            label = "Top Tracks", route = Screens.TopTrack.route
        )
    )
}