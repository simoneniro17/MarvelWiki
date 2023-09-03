package com.LCDP.marvelwiki.ui.screen

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.ComicViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

/*  Schermata principale. Viene fatto largo uso del metodo TextChip, un metodo Composable che
    permette di creare una label contenente un testo centrata nello schermo.    */
@Composable
fun HomeScreen(navController: NavController, context: Context) {

    //  Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Normal))

    //  Inizializzazione del repository e del ViewModel per i Comic
    val comicsRepository = ComicsRepository()
    val comicsViewModel: ComicsViewModel =
        viewModel(
            factory = ComicViewModelFactory(
                comicsRepository = comicsRepository,
                context.applicationContext as Application
            )
        )
    comicsViewModel.getLatestComic()

    //  Creazione della schermata
    val gradientColors = listOf(Color.Black, Color.Gray, Color.Black)
    val gradientBrush = Brush.verticalGradient(gradientColors)
    Box(
        modifier = Modifier
            .background(brush = gradientBrush)
            .fillMaxSize()
    ) {
        //  Immagine di sfondo che riempie l'intero Box
        Image(
            painter = painterResource(R.drawable.bg_prova),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )

        //  NOTA: ai NavigationButtons passare il navController per switchare schermata al click
        //  Creazione delle varie parti della schermata all'interno di una Column
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            //  Creazione della barra superiore
            HomeScreenUpperBar(currentFont)

            //  Creazione dei pulsanti di navigazione
            NavigationButtons(
                navController,
                currentFont
            )

            //  Creazione del banner "LATEST COMIC"
            LatestComicBanner(currentFont)

            //  Creazione della carta del LatestComic
            LatestComicCard(
                navController,
                context,
                comicsViewModel
            )
        }
    }
}

//  La barra superiore dell'homepage
@Composable
fun HomeScreenUpperBar(fontFamily: FontFamily) {
//    Color.Black, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Black
    //  La riga che costituirà la barra superiore
    val gradientColors = listOf(Color.Black, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Black)
    val gradientBrush = Brush.verticalGradient(gradientColors)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
    ) {
        Image(
            painter = painterResource(R.drawable.comic_bar),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(brush = gradientBrush)
                .border(border = BorderStroke(width = 1.dp, color = Color.Black))
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //  Testo centrato e in maiuscolo all'interno della barra
            Text(
                text = stringResource(R.string.home).uppercase(),
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
            )
        }
    }
}


//  I pulsanti di navigazione
@Composable
fun NavigationButtons(navController: NavController, fontFamily: FontFamily) {

    // Riga contenente la colonna rappresentante il bottone di navigazione dei personaggi
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //  Colonna per il primo bottone
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
            //  Immagine del primo bottone
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

            //  Testo del primo bottone
            val gradientColors = listOf(Color.Red, Color.White, Color.Red, Color.Red, Color.Red, Color.Red, Color.Black)
            val gradientBrush = Brush.verticalGradient(gradientColors)
            Text(
                text = stringResource(R.string.heroes).uppercase(),
                fontSize = 18.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(brush = gradientBrush)
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }

    // Riga contenente la colonna rappresentante il bottone di navigazione dei fumetti
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //  Colonna per il secondo bottone
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = { navController.navigate(Screens.ComicNavigationScreen.route) }),
            verticalArrangement = Arrangement.Top
        ) {
            //  Immagine del secondo bottone
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
            val gradientColors = listOf(Color.Red, Color.White, Color.Red, Color.Red, Color.Red, Color.Red, Color.Black)
            val gradientBrush = Brush.verticalGradient(gradientColors)
            //  Testo del secondo bottone
            Text(
                text = stringResource(R.string.library).uppercase(),
                fontSize = 18.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(brush = gradientBrush)
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

//  Banner del LatestComic
@Composable
fun LatestComicBanner(fontFamily: FontFamily) {

    //  Spaziatura verticale prima del banner
    Spacer(modifier = Modifier.height(5.dp))

    //  La riga che costituisce il banner
    val gradientColors = listOf(Color.Red, Color.White, Color.Red, Color.Red, Color.Red, Color.Black)
    val gradientBrush = Brush.verticalGradient(gradientColors)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = gradientBrush)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        //  Box per contenere il testo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(BorderStroke(1.dp, color = Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.comic_bar),
                contentDescription = "none",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            //  Testo del banner
            Text(
                text = stringResource(R.string.latest_comic).uppercase(),
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }

    //  Spaziatura verticale dopo il banner
    Spacer(modifier = Modifier.height(30.dp))
}                                                  //fontFamily needed for the "DAILY HERO" text

//  L'ultimo fumetto uscito
@Composable
fun LatestComicCard(navController: NavController, context: Context, comicsViewModel: ComicsViewModel) {

    Row {
        //  Spaziatura verticale prima del contenuto della card
        Spacer(modifier = Modifier.height(30.dp))

        // Colonna contenente il contenuto della card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //  Riquadro che contiene l'immagine cliccabile
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                ClickableImageCard(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    context = context,
                    comicsViewModel = comicsViewModel
                )
            }

            //  Spaziatura verticale tra la card e una riga di separazione
            Spacer(modifier = Modifier.height(30.dp))

            //  Riga di separazione visuale
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            ) {
                // Immagine di sfondo
                Image(
                    painter = painterResource(R.drawable.comic_bar),
                    contentDescription = "none",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

//  Card cliccabile con un determinato formato
@Composable
fun ClickableImageCard(navController: NavController, modifier: Modifier, context: Context, comicsViewModel: ComicsViewModel) {

    //  Card che se cliccata estrae i dettagli del primo fumetto dalla lista degli ultimi usciti
    Card(
        modifier = Modifier
            .height(300.dp)
            .width(200.dp)
            .clickable(onClick = {

                if (comicsViewModel.comicList.isNotEmpty()) {
                    val title = comicsViewModel.comicList[0].title
                    var thumbnail = comicsViewModel.comicList[0].thumbnail?.path
                    var description = comicsViewModel.comicList[0].description
                    val id = comicsViewModel.comicList[0].comicId
                    var isbn = comicsViewModel.comicList[0].isbn
                    val pageCount = comicsViewModel.comicList[0].pageCount
                    var series = comicsViewModel.comicList[0].series?.name

                    if (description.isNullOrEmpty()) {
                        description = "Not available"
                    }

                    if (isbn.isNullOrEmpty()) {
                        isbn = "Not available"
                    }

                    if (series.isNullOrEmpty()) {
                        series = "Not available"
                    }

                    thumbnail = thumbnail?.replace("/", "_")

                    //  Creazione di un argomento per la navigazione contenente i dettagli del fumetto
                    val args = listOf(
                        title, thumbnail, description, id, "YES", isbn, pageCount, series
                    )

                    //  Navigazione alla schermata dei dettagli del fumetto con gli argomenti
                    navController.navigate("comicScreen/${args.joinToString("/")}")
                }
            })
    ) {
        //  Box contenente l'immagine della copertina del fumetto
        Box(
            modifier = Modifier
                .height(300.dp)
                .padding(0.dp)
        ) {
            val imageView = remember { ImageView(context) }

            if (comicsViewModel.comicList.isNotEmpty()) {
                try {

                    //  Caricamento dell'immagine del fumetto utilizzando Picasso
                    Picasso.get()
                        .load((comicsViewModel.comicList[0].thumbnail?.path?.replace(
                                "http://",
                                "https://"
                            )) + ".jpg")
                        .placeholder(R.drawable.loading_placeholder)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .resize(600, 900)
                        .centerCrop()
                        .into(imageView)
                } catch (e: Exception) {
                    Log.e("PicassoError", "Error loading image: ${e.message}")
                }
            } else {
                // Caricamento di un'immagine placeholder quando la lista dei fumetti è vuota
                Picasso.get()
                    .load(R.drawable.loading_placeholder)
                    .resize(600, 900)
                    .centerCrop()
                    .into(imageView)
            }

            //  Utilizzo di AndroidView per visualizzare l'imageView
            AndroidView(
                factory = { imageView },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun TextChip(text: String, fontSize: TextUnit, fontFamily: FontFamily) {

    //  Creazione riquadro contenente il testo
    Box(
        modifier = Modifier
            .padding(25.dp)
            .border(
                border = BorderStroke(width = 1.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        //  Testo all'interno del riquadro
        Text(
            text = text.uppercase(),
            color = Color.White,
            fontSize = fontSize,
            fontFamily = fontFamily,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .background(Color.Transparent)
                .padding(10.dp)
        )
    }
}