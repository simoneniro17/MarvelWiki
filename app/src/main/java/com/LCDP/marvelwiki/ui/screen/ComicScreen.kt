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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.navArgument
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.HeroModel
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.model.ReadComic
import com.LCDP.marvelwiki.database.viewmodel.FavouriteCharacterViewModel
import com.LCDP.marvelwiki.database.viewmodel.FavouriteComicViewModel
import com.LCDP.marvelwiki.database.viewmodel.ReadComicViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.usefulStuff.Resource
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

//NOTA: non ho commentato le parti relative esclusivamente al layout e altri fattori grafici puramente estetici che non implementano alcuna funzionalità.
@Composable
fun ComicScreen(navController: NavController, arguments: List<String>, context : Context) {

    val comicTitle = arguments[0]
    val comicThumbnail = arguments[1]
    val comicDescription = arguments[2]
    val comicId = arguments[3]

    val appDatabase = appDatabase.getDatabase(context)
    val databaseAccess = DatabaseAccess(appDatabase)
    val favouriteComicViewModel = FavouriteComicViewModel(databaseAccess)
    val readComicViewModel = ReadComicViewModel(databaseAccess)

    //Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    //Setup dello sfondo
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.sfondo_muro),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            ComicScreenUpperBar(
                navController,
                currentFont,
                comicTitle
            )       //Costruzione della barra superiore (il navController è stato passato perchè la barra in questione contiene un tasto per tornare alla schermata di navigazione)
            ComicCard(
                currentFont,
                comicThumbnail,
                comicDescription,
                context,
                onFavoriteClicked = {isFavorite ->
                    if(isFavorite){
                        favouriteComicViewModel.insertData(FavouriteComic(comicId))
                        Log.i("TEST PREFERITO", comicThumbnail)
                    } else {
                        favouriteComicViewModel.deleteData(FavouriteComic(comicId))
                    }
                },
                onReadClicked = {isRead ->
                    if(isRead){
                        readComicViewModel.insertData(ReadComic(comicId))
                        Log.i("TEST LETTO", comicId)
                    } else {
                        readComicViewModel.deleteData(ReadComic(comicId))
                    }
                }

            )                          //Metodo riusabile che, se fornito di un model fumetto (che dovrà essere modificato in base alle info fornite dall' API), costruisce automaticamente la sua pagina)
        }

    }
}

@Composable
fun ComicScreenUpperBar(navController: NavController, fontFamily: FontFamily, comicTitle : String) {

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

        Column(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .border(border = BorderStroke(2.dp, color = Color.Black), shape = CircleShape)
                .clip(shape = CircleShape)
                .background(Color.Green)
        ) {
            Image(                                          //bottone per tornare indietro
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
                    .clickable(onClick = { navController.navigate(Screens.ComicNavigationScreen.route) })
            )
        }

        Text(
            text = comicTitle.uppercase(),
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun ComicCard(
    fontFamily: FontFamily,
    comicThumbnail : String,
    comicDescription : String,
    context : Context,
    onFavoriteClicked: (Boolean) -> Unit,
    onReadClicked: (Boolean) -> Unit) {  //Crea la lista contenente l'immagine del fumetto e i checkmark per segnare se il fumetto è stato letto o se è preferito
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
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                val imageView = remember { ImageView(context) }

                Picasso.get()
                    .load(comicThumbnail.replace("_","/").replace("http://", "https://") + ".jpg")
                    .placeholder(R.drawable.background_tamarro)                                                                            //attesa del carimento, da cmabiare
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color = Color.Red)
                    .border(border = BorderStroke(1.dp, Color.Black)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val checkedState1 = remember { mutableStateOf(false) }  //checkedState 1 e 2 tengono conto se il fumetto è, rispettivamente, uno dei preferiti e se è stato letto o meno.
                Checkbox(                                                      //Il resto del codice serve solo per la rappresentazione grafica dei checbox su cui clickare per mettere la spunta.
                    checked = checkedState1.value,
                    onCheckedChange = { checkedState1.value = it
                                      onFavoriteClicked(it)
                                      },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                    )
                )

                Text(
                    "Favorite".uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )

                val checkedState2 = remember { mutableStateOf(false) }
                Checkbox(
                    checked = checkedState2.value,
                    onCheckedChange = { checkedState2.value = it
                                      onReadClicked(it)},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                    )
                )

                Text(
                    "Read".uppercase(),
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    color = Color.White
                )
            }

            TextChip(comicDescription.uppercase(), 20.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}