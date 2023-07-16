package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            ComicNavigationScreen(navController = navController, context = context)
        }

        //Controller associato alla schermata del singolo fumetto
        //MODIFICHE TEMPORANEE PER PASSARE GLI ARGOMENTI (DA RIVEDERE)
        composable(route = Screens.ComicScreen.route, arguments = listOf(navArgument("selected_comic_id") {
            type = NavType.IntType
        })) {
            Log.d("Args", it.arguments?.getInt("selected_comic_id").toString())
            ComicScreen(navController = navController)
        }

    }
}