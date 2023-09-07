package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

    // Setup stringhe
    val addedToFav = stringResource(R.string.added_to_fav)
    val removedFromFav = stringResource(R.string.removed_from_fav)
    val addedToRead = stringResource(R.string.added_to_read)
    val removedFromRead = stringResource(R.string.removed_from_read)

    //Selezione della grandezza del font in modo tale da mostrare correttamente anche titoli particolarmente lunghi
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
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Black)
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.bg_prova),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )

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
                fontSize,
                isComicFavourite,
                onFavoriteClicked = { isFavorite ->
                    if (isFavorite) {
                        favouriteComicViewModel.insertData(FavouriteComic(comicId))
                        Toast.makeText(context, "$comicTitle $addedToFav", Toast.LENGTH_SHORT).show()
                    } else {
                        favouriteComicViewModel.deleteData(FavouriteComic(comicId))
                        Toast.makeText(context, "$comicTitle $removedFromFav", Toast.LENGTH_SHORT).show()
                    }

                }
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
                isComicRead,
                onReadClicked = { isRead ->
                    if (isRead) {
                        readComicViewModel.insertData(ReadComic(comicId))
                        Toast.makeText(context, "$comicTitle $addedToRead", Toast.LENGTH_SHORT).show()
                    } else {
                        readComicViewModel.deleteData(ReadComic(comicId))
                        Toast.makeText(context, "$comicTitle $removedFromRead", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

//  Barra superiore dei fumetti
@Composable
fun ComicScreenUpperBar(
    navController: NavController, fontFamily: FontFamily, comicTitle: String,
    isLatest: String, fontSize: TextUnit,isComicFavourite: MutableState<Boolean>,
    onFavoriteClicked: (Boolean) -> Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red.copy(alpha = 0.55f))
            .border(border = BorderStroke(width = (0.5).dp, color = Color.Black))
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
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
            modifier = Modifier
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
        ) {
            //  Icona per selezionare la preferenza
            FavouriteCheckbox(
                isComicFavourite.value,
                onCheckedChange = {
                    isComicFavourite.value = it
                    onFavoriteClicked(it)
                }
            )


        }



    }
}

//  Scheda per i dettagli del fumetto
@Composable
fun ComicCard(
    fontFamily: FontFamily, comicThumbnail: String, comicDescription: String,
    comicIsbn: String, comicPageCount: String, comicSeries: String, context: Context,
    isComicRead: MutableState<Boolean>, onReadClicked: (Boolean) -> Unit
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
                val imageView =ImageView(context)

                Picasso.get()
                    .load(comicThumbnail.replace("_", "/").replace("http://", "https://") + ".jpg")
                    .placeholder(R.drawable.comic_placeholder)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    //.networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(600, 900)
                    .centerCrop()
                    .into(imageView)

                AndroidView(
                    factory = { imageView },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            // Spaziatore
            Spacer(modifier = Modifier.height(10.dp))

            //  Sezione per aggiungere (rimuovere) il fumetto ai (dai) preferiti/letti
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Red.copy(alpha = 0.55f))
                    .border(border = BorderStroke(width = (0.5).dp, color = Color.Black)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //  Checkbox che permette di aggiungere/rimuovere il fumetto selezionato dalla lista dei fumetti letti
                ReadComicCheckbox(
                    isRead = isComicRead.value,
                    onReadChange = {
                        isComicRead.value = it
                        onReadClicked(it)
                    }
                )

                // Testo che accompagna la checkbox
                Text(
                    text = stringResource(R.string.read).uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )
            }

            val not_av = stringResource(R.string.not_available).uppercase()
            val descr = stringResource(R.string.description).uppercase()
            val code = stringResource(R.string.isbn).uppercase()

            //  Visualizza tutte le informazioni relative al fumetto selezionato
            if (comicDescription == not_av) {
                TextChip(stringResource(R.string.description_not_found).uppercase(), 15.sp, fontFamily)
            } else {
                TextChip(comicDescription.uppercase(), 15.sp, fontFamily)
            }

            if (comicIsbn == not_av) {
                TextChip(code + not_av, 15.sp, fontFamily)
            } else {
                TextChip(code + "$comicIsbn".uppercase(), 15.sp, fontFamily)
            }

            TextChip(stringResource(R.string.page_count) + " $comicPageCount".uppercase(), 15.sp, fontFamily)
            TextChip(stringResource(R.string.series)+ " $comicSeries".uppercase(), 15.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}