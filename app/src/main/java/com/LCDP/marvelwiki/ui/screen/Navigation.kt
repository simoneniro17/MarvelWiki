package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.LCDP.marvelwiki.data.model.Character

@Composable
fun Navigation(context: Context) {

    // Inizializzazione del controller del navigatore
    val navController = rememberNavController()

    // Vengono costruite le varie schermate, gli viene associato il controller e viene mostrata di default quella della Home
    NavHost(navController = navController, startDestination = Screens.StartingScreen.route) {

        // Controller associato alla schermata Home
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController, context)
        }

        // Controller associato alla schermata HeroNavigation
        composable(route = Screens.HeroNavigationScreen.route) {
            NavigationScreen(navController = navController, context = context)
        }

        // Controller associato alla schermata del singolo Eroe
        composable(route = Screens.HeroScreen.route) {
            //HeroScreen(navController = navController, selectedHero = selectedHero, context = context)
            HeroScreen(navController = navController)
        }

        // Controller associato alla schermata iniziale
        composable(route = Screens.StartingScreen.route) {
            StartingScreen(navController = navController)
        }

        // Controller associato alla schermata di navigazione dei fumetti
        composable(route = Screens.ComicNavigationScreen.route) {
            ComicNavigationScreen(navController = navController)
        }

        //DA AGGIUNGERE LE DESTINAZIONI NUOVE (ComicNavigationScreen, ComicFavoriteScreen, ComicScreen)
    }
}