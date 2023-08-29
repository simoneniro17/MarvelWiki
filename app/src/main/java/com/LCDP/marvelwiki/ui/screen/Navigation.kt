package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
            "heroScreen/{heroName}/{heroThumbnail}/{heroDescription}/{heroId}",
            arguments = listOf(
                navArgument("heroName") { type = NavType.StringType },
                navArgument("heroThumbnail") { type = NavType.StringType },
                navArgument("heroDescription") { type = NavType.StringType },
                navArgument("heroId") {type = NavType.StringType}
            )
        ) { entry ->
            val heroName = entry.arguments?.getString("heroName") ?: ""
            val heroThumbnail = entry.arguments?.getString("heroThumbnail") ?: ""
            val heroDescription = entry.arguments?.getString("heroDescription") ?: ""
            val heroId = entry.arguments?.getString("heroId") ?: ""
            HeroScreen(navController, listOf(heroName, heroThumbnail, heroDescription,heroId),context)
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
            "comicScreen/{comicTitle}/{comicThumbnail}/{comicDescription}/{comicId}/{isLatest}",
            arguments = listOf(
                navArgument("comicTitle") { type = NavType.StringType },
                navArgument("comicThumbnail") { type = NavType.StringType },
                navArgument("comicDescription") { type = NavType.StringType },
                navArgument("comicId") {type = NavType.StringType},
                navArgument("isLatest") {type = NavType.StringType}
            )
        ) { entry ->
            val comicTitle = entry.arguments?.getString("comicTitle") ?: ""
            val comicThumbnail = entry.arguments?.getString("comicThumbnail") ?: ""
            val comicDescription = entry.arguments?.getString("comicDescription") ?: ""
            val comicId = entry.arguments?.getString("comicId") ?: ""
            val isLatest = entry.arguments?.getString("isLatest") ?: ""
            ComicScreen(navController, listOf(comicTitle, comicThumbnail, comicDescription, comicId, isLatest), context)
        }

    }
}