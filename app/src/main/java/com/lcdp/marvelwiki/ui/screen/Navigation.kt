package com.lcdp.marvelwiki.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(context: Context) {

    //  Inizializzazione del controller del navigatore
    val navController = rememberNavController()

    //  Configurazione delle diverse schermate all'interno del NavHost
    NavHost(navController = navController, startDestination = Screens.StartingScreen.route) {

        //  Controller associato alla schermata HomeScreen
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController, context)
        }

        //  Controller associato alla schermata HeroNavigationScreen
        composable(route = Screens.HeroNavigationScreen.route) {
            NavigationScreen(navController = navController, context = context)
        }

        //  Controller associato alla schermata HeroScreen con argomenti
        composable(
            "heroScreen/{heroName}/{heroThumbnail}/{heroDescription}/{heroId}/{heroEvents}/{heroStories}/{heroComics}",
            arguments = listOf(
                navArgument("heroName") { type = NavType.StringType },
                navArgument("heroThumbnail") { type = NavType.StringType },
                navArgument("heroDescription") { type = NavType.StringType },
                navArgument("heroId") { type = NavType.StringType },
                navArgument("heroEvents") { type = NavType.StringType },
                navArgument("heroStories") { type = NavType.StringType },
                navArgument("heroComics") { type = NavType.StringType },
            )
        ) { entry ->

            //  Estrazione dei parametri dagli argomenti
            val heroName = entry.arguments?.getString("heroName") ?: ""
            val heroThumbnail = entry.arguments?.getString("heroThumbnail") ?: ""
            val heroDescription = entry.arguments?.getString("heroDescription") ?: ""
            val heroId = entry.arguments?.getString("heroId") ?: ""
            val heroEvents = entry.arguments?.getString("heroEvents") ?: ""
            val heroStories = entry.arguments?.getString("heroStories") ?: ""
            val heroComics = entry.arguments?.getString("heroComics") ?: ""

            //  Chiamata a HeroScreen con i parametri estratti
            HeroScreen(
                navController,
                listOf(
                    heroName,
                    heroThumbnail,
                    heroDescription,
                    heroId,
                    heroEvents,
                    heroStories,
                    heroComics
                ),
                context
            )
        }

        //  Controller associato alla schermata StartingScreen
        composable(route = Screens.StartingScreen.route) {
            StartingScreen(navController = navController)
        }

        //  Controller associato alla schermata ComicNavigationScreen
        composable(route = Screens.ComicNavigationScreen.route) {
            ComicNavigationScreen(navController = navController, context = context)
        }

        //  Controller associato alla schermata ComicScreen con argomenti
        composable(
            "comicScreen/{comicTitle}/{comicThumbnail}/{comicDescription}/{comicId}/{isLatest}/{comicIsbn}/{comicPageCount}/{comicSeries}",
            arguments = listOf(
                navArgument("comicTitle") { type = NavType.StringType },
                navArgument("comicThumbnail") { type = NavType.StringType },
                navArgument("comicDescription") { type = NavType.StringType },
                navArgument("comicId") { type = NavType.StringType },
                navArgument("isLatest") { type = NavType.StringType },
                navArgument("comicIsbn") { type = NavType.StringType },
                navArgument("comicPageCount") { type = NavType.StringType },
                navArgument("comicSeries") { type = NavType.StringType }
            )
        ) { entry ->

            //  Estrazione dei parametri dagli argomenti
            val comicTitle = entry.arguments?.getString("comicTitle") ?: ""
            val comicThumbnail = entry.arguments?.getString("comicThumbnail") ?: ""
            val comicDescription = entry.arguments?.getString("comicDescription") ?: ""
            val comicId = entry.arguments?.getString("comicId") ?: ""
            val isLatest = entry.arguments?.getString("isLatest") ?: ""
            val comicIsbn = entry.arguments?.getString("comicIsbn") ?: ""
            val comicPageCount = entry.arguments?.getString("comicPageCount") ?: ""
            val comicSeries = entry.arguments?.getString("comicSeries") ?: ""

            //  Chiamata a ComicScreen con i parametri estratti
            ComicScreen(
                navController,
                listOf(
                    comicTitle,
                    comicThumbnail,
                    comicDescription,
                    comicId,
                    isLatest,
                    comicIsbn,
                    comicPageCount,
                    comicSeries
                ),
                context
            )
        }
    }
}