package com.LCDP.marvelwiki.ui.screen

// Classe che definisce una gerarchia di oggetti Singleton che rappresentano le diverse schermate dell'applicazione
sealed class Screens(val route : String) {
    object HomeScreen : Screens("home_screen")
    object NavigationScreen : Screens("nav_screen")
    object FavoriteScreen : Screens("fav_screen")
    object HeroScreen : Screens("hero_screen")
}