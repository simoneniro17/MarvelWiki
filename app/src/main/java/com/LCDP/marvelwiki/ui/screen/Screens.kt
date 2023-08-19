package com.LCDP.marvelwiki.ui.screen

// Classe che definisce una gerarchia di oggetti Singleton che rappresentano le diverse schermate dell'applicazione
sealed class Screens(val route: String) {
    object HomeScreen : Screens(route = "home_screen")
    object HeroNavigationScreen : Screens(route = "hero_nav_screen")
    object HeroScreen : Screens(route = "hero_screen")
    object ComicNavigationScreen : Screens(route = "comic_nav_screen")

    //MODIFICHE TEMPORANEE PER PASSARE GLI ARGOMENTI (DA RIVEDERE)
    object ComicScreen : Screens(route = "comic_screen/{selected_comic_id}") {
        fun passId(id : Int) : String {
            return "comic_screen/$id"
        }
    }
    object StartingScreen : Screens(route = "starting_screen")
}