package com.LCDP.marvelwiki.ui.screen

import android.content.Context
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
            //  Barra superiore della schermata dell'eroe
            HeroScreenUpperBar(
                navController,
                currentFont,
                selectedHeroName
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
                isFavorite,
                onFavoriteClicked = { isFavorite ->
                    if (isFavorite) {
                        favouriteCharacterViewModel.insertData(FavouriteCharacter(selectedHeroId))
                    } else {
                        favouriteCharacterViewModel.deleteData(FavouriteCharacter(selectedHeroId))
                    }
                }
            )
        }
    }
}

//  Barra superiore con tasto di ritorno e nome dell'eroe
@Composable
fun HeroScreenUpperBar(navController: NavController, fontFamily: FontFamily, selectedHeroName: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(70.dp),
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
                    .clickable(onClick = { navController.navigate(Screens.HeroNavigationScreen.route) })
            )
        }

        //  Nome dell'eroe visualizzato nella barra superiore
        Text(
            text = selectedHeroName.uppercase(),
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )
    }
}

//  Scheda per i dettagli dell'eroe
@Composable
fun HeroCard(fontFamily: FontFamily, selectedHeroThumbnail: String, selectedHeroDescription: String,
             selectedHeroEvents: String, selectedHeroStories: String, selectedHeroComics: String,
             context: Context, isFavorite: MutableState<Boolean>, onFavoriteClicked: (Boolean) -> Unit) {

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
                val imageView = remember { ImageView(context) }

                //  Caricamento immagine dell'eroe
                Picasso.get()
                    .load(
                        selectedHeroThumbnail.replace("_", "/")
                            .replace("http://", "https://") + ".jpg"
                    )
                    .placeholder(R.drawable.hero_placeholder)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(900, 600)
                    .centerCrop()
                    .into(imageView)

                //  Visualizzazione immagine
                AndroidView(
                    factory = { imageView },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .border(
                            border = BorderStroke(width = 1.dp, Color.Black),
                            shape = RectangleShape
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //  Sezione per aggiungere (rimuovere) l'eroe ai (dai) preferiti
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.Red)
                    .border(border = BorderStroke(1.dp, Color.Black)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //  Icona per i preferiti
                FavouriteCheckbox(
                    isFavorite.value,
                    onCheckedChange = {
                        isFavorite.value = it
                        onFavoriteClicked(it)
                    }
                )

                //  Testo "preferiti" vicino l'icona
                Text(
                    text = stringResource(R.string.favorite).uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )
            }

            //  Visualizza la descrizione dell'eroe
            if (selectedHeroDescription == "DESCRIPTION NOT FOUND") {
                TextChip("Description: " + stringResource(R.string.description_not_found), 15.sp, fontFamily)
            } else {
                TextChip("Description: $selectedHeroDescription", 15.sp, fontFamily)
            }

            //  Visualizza numero di eventi, storie e fumetti dell'eroe
            TextChip(stringResource(R.string.events) + " $selectedHeroEvents", 15.sp, fontFamily)
            TextChip(stringResource(R.string.stories) + " $selectedHeroStories", 15.sp, fontFamily)
            TextChip(
                stringResource(R.string.comics_explanation) + " $selectedHeroComics",
                15.sp,
                fontFamily
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}