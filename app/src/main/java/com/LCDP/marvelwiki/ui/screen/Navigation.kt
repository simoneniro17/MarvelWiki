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
        composable(
            "heroScreen/{heroName}/{heroThumbnail}/{heroDescription}",
            arguments = listOf(
                navArgument("heroName") { type = NavType.StringType },
                navArgument("heroThumbnail") { type = NavType.StringType },
                navArgument("heroDescription") { type = NavType.StringType }
            )
        ) { entry ->
            val heroName = entry.arguments?.getString("heroName") ?: ""
            val heroThumbnail = entry.arguments?.getString("heroThumbnail") ?: ""
            val heroDescription = entry.arguments?.getString("heroDescription") ?: ""
            HeroScreen(navController, listOf(heroName, heroThumbnail, heroDescription))
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
        composable(
            "comicScreen/{comicTitle}/{comicThumbnail}/{comicDescription}/{comicId}",
            arguments = listOf(
                navArgument("comicTitle") { type = NavType.StringType },
                navArgument("comicThumbnail") { type = NavType.StringType },
                navArgument("comicDescription") { type = NavType.StringType },
                navArgument("comicId") {type = NavType.IntType}
            )
        ) { entry ->
            val comicTitle = entry.arguments?.getString("comicTitle") ?: ""
            val comicThumbnail = entry.arguments?.getString("comicThumbnail") ?: ""
            val comicDescription = entry.arguments?.getString("comicDescription") ?: ""
            val comicId = entry.arguments?.getString("comicId") ?: ""
            ComicScreen(navController, listOf(comicTitle, comicThumbnail, comicDescription, comicId))
        }

    }
}