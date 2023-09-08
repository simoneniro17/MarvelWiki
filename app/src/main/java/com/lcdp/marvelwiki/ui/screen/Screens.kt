package com.lcdp.marvelwiki.ui.screen

// Classe che definisce una gerarchia di oggetti Singleton che rappresentano le diverse schermate dell'applicazione
sealed class Screens(val route: String) {
    object HomeScreen : Screens(route = "home_screen")
    object HeroNavigationScreen : Screens(route = "hero_nav_screen")
    object HeroScreen : Screens(route = "heroScreen")
    object ComicNavigationScreen : Screens(route = "comic_nav_screen")
    object ComicScreen : Screens(route = "comicScreen")
    object StartingScreen : Screens(route = "starting_screen")
}