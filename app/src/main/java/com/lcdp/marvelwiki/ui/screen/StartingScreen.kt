package com.lcdp.marvelwiki.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.lcdp.marvelwiki.R

//  Creazione della schermata d'avvio
@Composable
fun StartingScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        //  Immagine di sfondo che riempie l'intero spazio del Box
        Image(
            painter = painterResource(R.drawable.background_colored),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        //  Colonna che centra il suo contenuto
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //  Immagine del logo degli Avengers che, se cliccata, porta alla HomeScreen
            Image(
                painter = painterResource(R.drawable.avengers_logo),
                contentDescription = "none",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(Screens.HomeScreen.route) }
            )
        }
    }
}