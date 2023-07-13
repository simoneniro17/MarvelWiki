package com.LCDP.marvelwiki.ui.screen

// Classe che definisce una gerarchia di oggetti Singleton che rappresentano le diverse schermate dell'applicazione
sealed class Screens(val route : String) {
    object HomeScreen : Screens("home_screen")
    object HeroNavigationScreen : Screens("hero_nav_screen")
    object HeroFavoriteScreen : Screens("hero_fav_screen")
    object HeroScreen : Screens("hero_screen")
    object ComicNavigationScreen : Screens("comic_nav_screen")
    object ComicFavoriteScreen : Screens("comic_fav_screen")
    object ComicScreen : Screens("comic_screen")
    object StartingScreen : Screens("starting_screen")
}