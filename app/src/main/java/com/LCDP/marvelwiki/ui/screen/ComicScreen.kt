package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.AppDatabase
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.model.ReadComic
import com.LCDP.marvelwiki.database.viewmodel.FavouriteComicViewModel
import com.LCDP.marvelwiki.database.viewmodel.ReadComicViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

//  Schermata al dettaglio di un fumetto
@Composable
fun ComicScreen(navController: NavController, arguments: List<String>, context: Context) {

    //  Estrazione degli argomenti passati alla schermata
    val comicTitle = arguments[0]
    val comicThumbnail = arguments[1]
    val comicDescription = arguments[2]
    val comicId = arguments[3]
    val isLatest = arguments[4]
    val comicIsbn = arguments[5]
    val comicPageCount = arguments[6]
    val comicSeries = arguments[7]

    val fontSize: TextUnit = if (comicTitle.length <= 28) {
        20.sp
    } else {
        15.sp
    }

    //  Inizializzazione DB e ViewModel per i fumetti preferiti e letti
    val appDatabase = AppDatabase.getDatabase(context)
    val databaseAccess = DatabaseAccess(appDatabase)
    val favouriteComicViewModel = FavouriteComicViewModel(databaseAccess)
    val readComicViewModel = ReadComicViewModel(databaseAccess)

    //  Stato per verificare se il fumetto è letto e/o nei preferiti
    val isComicFavourite = remember { mutableStateOf(false) }
    val isComicRead = remember { mutableStateOf(false) }

    //  Controlla se il fumetto è nei letti e/o nei preferiti
    LaunchedEffect(comicId) {
        isComicFavourite.value = favouriteComicViewModel.isComicFavourite(comicId)
        isComicRead.value = readComicViewModel.isComicRead(comicId)
    }

    //  Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    //  Creazione del layout della schermata
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.LightGray, Color.Black)
                )
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            //  Barra superiore della schermata del fumetto
            ComicScreenUpperBar(
                navController,
                currentFont,
                comicTitle,
                isLatest,
                fontSize
            )

            //  Composable che mostra i dettagli del fumetto
            ComicCard(
                currentFont,
                comicThumbnail,
                comicDescription,
                comicIsbn,
                comicPageCount,
                comicSeries,
                context,
                isComicFavourite,
                isComicRead,
                onFavoriteClicked = { isFavorite ->
                    if (isFavorite)
                        favouriteComicViewModel.insertData(FavouriteComic(comicId))
                    else
                        favouriteComicViewModel.deleteData(FavouriteComic(comicId))

                },
                onReadClicked = { isRead ->
                    if (isRead)
                        readComicViewModel.insertData(ReadComic(comicId))
                    else
                        readComicViewModel.deleteData(ReadComic(comicId))
                }
            )
        }
    }
}

//  Barra superiore dei fumetti
@Composable
fun ComicScreenUpperBar(
    navController: NavController, fontFamily: FontFamily, comicTitle: String,
    isLatest: String, fontSize: TextUnit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //  Tasto di ritorno alla schermata di navigazione
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .border(border = BorderStroke(2.dp, color = Color.Black), shape = CircleShape)
                .clip(shape = CircleShape)
                .background(Color.Green)
        ) {
            //  Icona per tornare indietro
            Image(
                painterResource(R.drawable.back_arrow),
                contentDescription = "HOME",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .border(border = BorderStroke(width = 1.dp, Color.Black), shape = CircleShape)
                    .clip(shape = CircleShape)
                    .clickable(onClick = {
                        if (isLatest == "YES")
                            navController.navigate(Screens.HomeScreen.route)
                        else
                            navController.navigate(Screens.ComicNavigationScreen.route)
                    })
            )
        }

        //  Nome del fumetto visualizzato nella barra superiore
        Text(
            text = comicTitle.uppercase(),
            fontSize = fontSize,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )
    }
}

//  Scheda per i dettagli del fumetto
@Composable
fun ComicCard(
    fontFamily: FontFamily, comicThumbnail: String, comicDescription: String,
    comicIsbn: String, comicPageCount: String, comicSeries: String, context: Context,
    isComicFavourite: MutableState<Boolean>, isComicRead: MutableState<Boolean>,
    onFavoriteClicked: (Boolean) -> Unit, onReadClicked: (Boolean) -> Unit
) {

    Row {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //  Separatore verticale dalla barra superiore
            Spacer(modifier = Modifier.height(10.dp))

            //  Immagine del fumetto
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                val imageView = remember { ImageView(context) }

                Picasso.get()
                    .load(comicThumbnail.replace("_", "/").replace("http://", "https://") + ".jpg")
                    .placeholder(R.drawable.comic_placeholder)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(800, 800)
                    .centerCrop()
                    .into(imageView)

                AndroidView(
                    factory = { imageView },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //  Sezione per aggiungere (rimuovere) il fumetto ai (dai) preferiti/letti
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.Red)
                    .border(border = BorderStroke(1.dp, Color.Black)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isComicFavourite.value,
                    onCheckedChange = {
                        isComicFavourite.value = it
                        onFavoriteClicked(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                        //checkmarkColor = Color.Black
                    )
                )

                Text(
                    "Favorite".uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )

                Checkbox(
                    checked = isComicRead.value,
                    onCheckedChange = {
                        isComicRead.value = it
                        onReadClicked(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                        //checkmarkColor = Color.Black
                    )
                )

                Text(
                    "Read".uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )
            }

            //  Visualizza codice ISBN, numero di pagine e serie di appartenza del fumetto
            TextChip(comicDescription.uppercase(), 20.sp, fontFamily)
            TextChip("ISBN: $comicIsbn", 20.sp, fontFamily)
            TextChip("Page count: $comicPageCount", 20.sp, fontFamily)
            TextChip("Series: $comicSeries", 20.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}