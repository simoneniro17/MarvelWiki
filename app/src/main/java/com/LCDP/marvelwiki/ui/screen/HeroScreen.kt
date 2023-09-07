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
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.viewmodel.FavouriteCharacterViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

//  Schermata al dettaglio di un personaggio
@Composable
fun HeroScreen(navController: NavController, arguments: List<String>, context: Context) {

    //  Estrazione degli argomenti passati alla schermata
    val selectedHeroName = arguments[0]
    val selectedHeroThumbnail = arguments[1]
    val selectedHeroDescription = arguments[2]
    val selectedHeroId = arguments[3]
    val selectedHeroEvents = arguments[4]
    val selectedHeroStories = arguments[5]
    val selectedHeroComics = arguments[6]

    //Adattamento dimensioni testo per nomi di eroi troppo lunghi
    val fontSize: TextUnit = if (selectedHeroName.length <= 15) {
        20.sp
    } else {
        12.sp
    }

    //Setup stringhe
    val addedToFav = stringResource(R.string.added_to_fav)
    val removedFromFav = stringResource(R.string.removed_from_fav)

    //  Inizializzazione DB e ViewModel per i personaggi preferiti
    val appDatabase = AppDatabase.getDatabase(context)
    val databaseAccess = DatabaseAccess(appDatabase)
    val favouriteCharacterViewModel = FavouriteCharacterViewModel(databaseAccess)

    //  Stato per verificare se il personaggio è nei preferiti
    val isFavorite = remember { mutableStateOf(false) }

    //  Controlla se il personaggio è nei preferiti
    LaunchedEffect(selectedHeroId) {
        isFavorite.value = favouriteCharacterViewModel.isCharacterFavourite(selectedHeroId)
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
            //  Barra superiore della schermata dell'eroe
            HeroScreenUpperBar(
                navController,
                currentFont,
                selectedHeroName,
                fontSize,
                isFavorite,
                onFavoriteClicked = { isFavorite ->
                    if (isFavorite) {
                        favouriteCharacterViewModel.insertData(FavouriteCharacter(selectedHeroId))
                        Toast.makeText(context, "$selectedHeroName $addedToFav", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        favouriteCharacterViewModel.deleteData(FavouriteCharacter(selectedHeroId))
                        Toast.makeText(
                            context,
                            "$selectedHeroName $removedFromFav",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            )

            //  Composable che mostra i dettagli dell'eroe
            HeroCard(
                currentFont,
                selectedHeroThumbnail,
                selectedHeroDescription,
                selectedHeroEvents,
                selectedHeroStories,
                selectedHeroComics,
                context,
            )
        }
    }
}

//  Barra superiore con tasto di ritorno e nome dell'eroe
@Composable
fun HeroScreenUpperBar(navController: NavController, fontFamily: FontFamily, selectedHeroName: String, fontSize : TextUnit,
                       isFavorite: MutableState<Boolean>, onFavoriteClicked: (Boolean) -> Unit) {

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
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .clickable(onClick = { navController.navigate(Screens.HeroNavigationScreen.route) })

            )
        }

        //  Nome dell'eroe visualizzato nella barra superiore
        Text(
            text = selectedHeroName.uppercase(),
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
            //  Icona per tornare indietro
            FavouriteCheckbox(
                isFavorite.value,
                onCheckedChange = {
                    isFavorite.value = it
                    onFavoriteClicked(it)
                }
            )


        }


    }
}

//  Scheda per i dettagli dell'eroe
@Composable
fun HeroCard(fontFamily: FontFamily, selectedHeroThumbnail: String, selectedHeroDescription: String,
             selectedHeroEvents: String, selectedHeroStories: String, selectedHeroComics: String,
             context: Context) {

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

            //  Immagine dell'eroe
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                val imageView = ImageView(context)

                //  Caricamento immagine dell'eroe
                Picasso.get()
                    .load(selectedHeroThumbnail.replace("_", "/").replace("http://", "https://") + ".jpg")
                    .placeholder(R.drawable.hero_placeholder)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    //.networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(900, 600)
                    .centerCrop()
                    .into(imageView)

                //  Visualizzazione immagine
                AndroidView(
                    factory = { imageView },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //  Separatore
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Red.copy(alpha = 0.55f))
                    .border(border = BorderStroke(width = (0.5).dp, color = Color.Black)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.info).uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )
            }

            //  Visualizza la descrizione dell'eroe
            if (selectedHeroDescription == "NOT AVAILABLE") {
                TextChip(stringResource(R.string.description_not_found).uppercase(), 15.sp, fontFamily)
            } else {
                TextChip(selectedHeroDescription.uppercase(), 15.sp, fontFamily)
            }

            //  Visualizza numero di eventi, storie e fumetti dell'eroe
            TextChip(stringResource(R.string.events) + " $selectedHeroEvents", 15.sp, fontFamily)
            TextChip(stringResource(R.string.stories) + " $selectedHeroStories", 15.sp, fontFamily)
            TextChip(stringResource(R.string.comics_explanation) + " $selectedHeroComics", 15.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}