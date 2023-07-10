package com.LCDP.marvelwiki.ui.screen

sealed class Screens(val route : String) {
    object HomeScreen : Screens("home_screen")
    object NavigationScreen : Screens("nav_screen")
    object FavoriteScreen : Screens("fav_screen")
    object HeroScreen : Screens("hero_screen")
}