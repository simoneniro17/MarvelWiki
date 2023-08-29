package com.LCDP.marvelwiki.ui.screen

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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

//aaaaa
//SCHERMATA HOME
@Composable
fun HomeScreen(navController: NavController, context: Context) {

    //In questa classe e nella classe HeroScreen si fa largamente uso del metodo TextChip. E' un metodo composable che permette,
    // dati come argomenti un font, la dimensione del testo e il testo, di creare una piccola label contenente il testo inserito, automaticamente
    // centrata nello schermo. Le TextChip sono facilmente riusabili per aggiungere serie di informazioni su un personaggio.

    //Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Normal))

    val comicsRepository = ComicsRepository()
    val comicsViewModel: ComicsViewModel = viewModel(factory = ComicViewModelFactory(comicsRepository = comicsRepository, context.applicationContext as Application))
    comicsViewModel.getLatestComic()
    //CREAZIONE DELLA SCHERMATA INTERA
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background_tamarro),
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
            HomeScreenUpperBar(currentFont)                                               //Setup of the Layout Bar displaying Home
            NavigationButtons(navController, currentFont)
            LatestComicBanner(currentFont) //Setup of the "DAILY HERO" banner - mid screen
            LatestComicCard(
                navController,
                currentFont,
                context,
                comicsViewModel
            )
              // +INSERIRE COMIC MODEL)       //Setup of the scrollable daily hero card (same card as the navigation ones) - bottom screen

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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.home).uppercase(),
            fontSize = 30.sp,
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
                text = stringResource(R.string.heroes).uppercase(),
                fontSize = 18.sp,
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
                .clickable(onClick = { navController.navigate(Screens.ComicNavigationScreen.route) }),
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
                text = stringResource(R.string.library).uppercase(),
                fontSize = 18.sp,
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
                text = stringResource(R.string.latest_comic).uppercase(),
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
}                                                  //fontFamily needed for the "DAILY HERO" text

@Composable
fun LatestComicCard(
    navController: NavController,
    fontFamily: FontFamily,
    context: Context,
    comicsViewModel: ComicsViewModel
) { //INSERIRE COMIC MODEL
    Row {
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                ClickableImageCard(
                    navController = navController,
                    contentDescription = "None",
                    modifier = Modifier.fillMaxSize(),
                    context = context,
                    comicsViewModel = comicsViewModel
                )


            }

            Spacer(modifier = Modifier.height(30.dp))

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
    context: Context,
    comicsViewModel: ComicsViewModel
) {

    Card(
        modifier = Modifier
            .height(300.dp)
            .width(200.dp)
            .clickable(onClick = {

                val title = comicsViewModel.comicList[0].title
                var thumbnail = comicsViewModel.comicList[0].thumbnail?.path
                thumbnail = thumbnail?.replace("/","_")
                var description = comicsViewModel.comicList[0].description

                if (description.isNullOrEmpty()) {
                    description = "DESCRIPTION NOT FOUND"
                }

                val id = comicsViewModel.comicList[0].comicId

                val args = listOf(
                    title,
                    thumbnail,
                    description,
                    id,
                    "YES"
                )
                navController.navigate("comicScreen/${args.joinToString("/")}")

            })
            .border(
                border = BorderStroke(width = 2.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .padding(0.dp)
        ) {
            val imageView = remember { ImageView(context) }

            if (comicsViewModel.comicList.isNotEmpty()) {
                try {
                    Picasso.get()
                        .load(
                            (comicsViewModel.comicList[0].thumbnail?.path?.replace(
                                "http://",
                                "https://"
                            )) + ".jpg"
                        )
                        .placeholder(R.drawable.loading_placeholder)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .resize(200, 300)
                        .centerCrop()
                        .into(imageView)
                } catch (e: Exception) {
                    Log.e("PicassoError", "Error loading image: ${e.message}")
                }
            } else {
                // Load a placeholder image when the comicList is empty
                Picasso.get()
                    .load(R.drawable.loading_placeholder)
                    .resize(200, 300)
                    .centerCrop()
                    .into(imageView)
            }






            AndroidView(
                factory = { imageView },
                modifier = Modifier.fillMaxSize()
            )
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
                border = BorderStroke(width = 1.dp, Color.Black),
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