package com.LCDP.marvelwiki.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.HeroModel
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.printer.RetrieveLatestComic
import com.squareup.picasso.Picasso


//SCHERMATA HOME
@Composable
fun HomeScreen(navController: NavController) {

    //In questa classe e nella classe HeroScreen si fa largamente uso del metodo TextChip. E' un metodo composable che permette,
    // dati come argomenti un font, la dimensione del testo e il testo, di creare una piccola label contenente il testo inserito, automaticamente
    // centrata nello schermo. Le TextChip sono facilmente riusabili per aggiungere serie di informazioni su un personaggio.

    //Setup del font
    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))

    //CREAZIONE DELLA SCHERMATA INTERA
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        //Costruzione dei dettagli (ho associato ad ogni parte della schermata un metodo (evidenziato in rosso) che la crea per motivi di ordine)
        //DA NOTARE che ai NavigationButtons deve essere passato il navController, per poterlo utilizzare per switchare schermata quando clicchi il bottone)
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            HomeScreenUpperBar(marvelFont)                                               //Setup of the Layout Bar displaying Home
            NavigationButtons(navController, marvelFont)
            LatestComicBanner(marvelFont)                                                  //Setup of the "DAILY HERO" banner - mid screen
            LatestComicCard(
                navController,
                marvelFont
            )  // +INSERIRE COMIC MODEL)       //Setup of the scrollable daily hero card (same card as the navigation ones) - bottom screen
        }
    }
}

@Composable
fun HomeScreenUpperBar(fontFamily: FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "home".uppercase(),
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun NavigationButtons(navController: NavController, fontFamily: FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = { navController.navigate(Screens.HeroNavigationScreen.route) }),
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painterResource(R.drawable.avengers),
                contentDescription = "HEROES",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .width(3000.dp)
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black)
                    )
            )

            Text(
                text = "HEROES".uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color.Red)
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )

        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = { navController.navigate(Screens.ComicNavigationScreen.route) }), // TODO
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painterResource(R.drawable.library),
                contentDescription = "LIBRARY",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .width(300.dp)
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black),
                    )
            )

            Text(
                text = "LIBRARY".uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color.Red)
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )

        }
    }
}

@Composable
fun LatestComicBanner(fontFamily: FontFamily) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(BorderStroke(1.dp, color = Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LATEST COMIC",
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}                                                  //fontFamily needed for the "DAILY HERO" text

@Composable
fun LatestComicCard(navController: NavController, fontFamily: FontFamily) { //INSERIRE COMIC MODEL
    println("ciao")
    Row {
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                ClickableImageCard(
                    navController,
                    painter = painterResource(R.drawable.holo_globe),
                    contentDescription = "None",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Color.Red)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black))
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("")
            }

        }
    }

}

@Composable
fun ClickableImageCard(
    navController: NavController,
    contentDescription: String,
    modifier: Modifier,
    painter: Painter
    // SE VUOI RIPRISTINARE AGGIUNGI painter:Painter
) {
    println("ciao")
    val comicsRepository = ComicsRepository()
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable(onClick = { /*TODO ti porta alla pagina specifica del fumetto*/ })
            .border(
                border = BorderStroke(width = 2.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds
            )

            /* PicassoImage(
                 data = RetrieveLatestComic(comicsRepository = comicsRepository), // Inserisci qui l'URL dell'immagine presa dalla API
                 contentDescription = contentDescription,
                 modifier = Modifier.fillMaxSize(),
                 picasso = Picasso.get(),
                 loading = {
                     // Stato di caricamento dell'immagine
                     // Puoi mostrare un indicatore di caricamento qui
                 },
                 error = {
                     // Stato di errore dell'immagine
                     // Puoi mostrare un'immagine di errore o un messaggio di errore qui
                 }
             ) */
        }
    }
}

@Composable
fun UnclickableImageCard(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .border(
                border = BorderStroke(width = 2.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun TextChip(text: String, fontSize: TextUnit, fontFamily: FontFamily) {

    val text = text

    Spacer(modifier = Modifier.height(5.dp))

    Box(
        modifier = Modifier
            .padding(30.dp)
            .border(
                border = BorderStroke(width = 1.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            color = Color.White,
            fontSize = fontSize,
            fontFamily = fontFamily,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .background(Color.Black)
                .padding(10.dp)
        )
    }

}