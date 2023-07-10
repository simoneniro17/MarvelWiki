package com.LCDP.marvelwiki.ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FavoriteScreen(navController : NavController) {
    //Setup dei personaggi indicati come preferiti
    FavoriteHeroesList(0)
}

@Composable
fun FavoriteHeroesList(howManyFavs : Int) {
    //Se non ci sono preferiti, un messaggio avvisa che la schermata è vuota, altrimenti chiama il metodo SetUpFavorites
    if (howManyFavs == 0) {
        Text(text = "There are no favorites here")
    } else {
        SetUpFavorites()
    }
}

@Composable
fun SetUpFavorites() {
    Text(text = "To do") //Questo metodo recupererà dal database tutti i personaggi indicati dall' utente come preferiti e li metterà in una lazyList
    //In maniera del tutto analoga a quella della NavigationScreen.
}