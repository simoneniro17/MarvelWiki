package com.LCDP.marvelwiki.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {

    //Inizializzazione del controller del navigatore
    val navController = rememberNavController()
    //Vengono costruite le varie schermate, gli viene associato il controller e viene mostrata di default quella della Home
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        //Controller associato alla schermata Home
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        //Controller associato alla schermata Navigation
        composable(route = Screens.NavigationScreen.route) {
            NavigationScreen(navController = navController)
        }

        //Controller associato alla schermata Favorites
        composable(route = Screens.FavoriteScreen.route) {
            FavoriteScreen(navController = navController)
        }

        //Controller associato alla schermata del singolo Eroe
        composable(route = Screens.HeroScreen.route) {
            HeroScreen(navController = navController)
        }
    }
}