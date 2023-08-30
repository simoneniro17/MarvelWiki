package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.view.ViewGroup
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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

@Composable
fun HeroScreen(navController: NavController, arguments: List<String>, context: Context) {

    //SELEZIONE ARGOMENTI
    val selectedHeroName = arguments[0]
    val selectedHeroThumbnail = arguments[1]
    val selectedHeroDescription = arguments[2]
    val selectedHeroId = arguments[3]
    val selectedHeroEvents = arguments[4]
    val selectedHeroStories = arguments[5]
    val selectedHeroComics = arguments[6]

    //SETUP DATABASE
    val appDatabase = AppDatabase.getDatabase(context)
    val databaseAccess = DatabaseAccess(appDatabase)
    val favouriteCharacterViewModel = FavouriteCharacterViewModel(databaseAccess)

    val isFavorite = remember {mutableStateOf(false)}

    LaunchedEffect(selectedHeroId) {
        val isFav = favouriteCharacterViewModel.isCharacterFavourite(selectedHeroId)
        isFavorite.value = isFav
    }

   //Setup del font
   val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

   Box(
       modifier = Modifier
           .background(brush = Brush.verticalGradient(
               colors = listOf(Color.LightGray, Color.Black)
           ))
           .fillMaxSize()
   ) {
       /*
       Image(
           painter = painterResource(R.drawable.sfondo_muro),
           contentDescription = "none",
           contentScale = ContentScale.FillBounds,
           modifier = Modifier.fillMaxSize()
       ) */
       Column(
           modifier = Modifier
               .background(Color.Transparent)
               .fillMaxSize(),
           verticalArrangement = Arrangement.Top
       ) {

           HeroScreenUpperBar(
               navController,
               currentFont,
               selectedHeroName
           )       //Costruzione della barra superiore (il navController è stato passato perchè la barra in questione contiene un tasto per tornare alla schermata di navigazione)
          HeroCard(
              currentFont,
              selectedHeroThumbnail,
              selectedHeroDescription,
              selectedHeroEvents,
              selectedHeroStories,
              selectedHeroComics,
               context,
               isFavorite,
               onFavoriteClicked = {isFavorite ->
                   if(isFavorite){
                       favouriteCharacterViewModel.insertData(FavouriteCharacter(selectedHeroId))
                   } else {
                       favouriteCharacterViewModel.deleteData(FavouriteCharacter(selectedHeroId))
                   }
               }
          )
       }

   }
}

@Composable
fun HeroScreenUpperBar(
    navController: NavController,
    fontFamily: FontFamily,
    selectedHeroName: String
) {

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

       Column(
           modifier = Modifier
               .height(40.dp)
               .width(40.dp)
               .border(border = BorderStroke(2.dp, color = Color.Black), shape = CircleShape)
               .clip(shape = CircleShape)
               .background(Color.Green)
       ) {
           Image(
               painterResource(R.drawable.back_arrow),    //bottone per tornare indietro
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

       Text(
           text = selectedHeroName.uppercase(),
           fontSize = 20.sp,
           color = Color.White,
           fontFamily = fontFamily,
           textAlign = TextAlign.Center,
       )

   }
}

@Composable
fun HeroCard(
    fontFamily: FontFamily,
    selectedHeroThumbnail: String,
    selectedHeroDescription: String,
    selectedHeroEvents: String,
    selectedHeroStories: String,
    selectedHeroComics: String,
    context: Context,
    isFavorite : MutableState<Boolean>,
    onFavoriteClicked: (Boolean) -> Unit
) {   //Crea la lista contenente l'immagine dell'eroe e i checkmark per segnare se è preferito
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
                   .load(selectedHeroThumbnail.replace("_","/").replace("http://", "https://") + ".jpg")
                   .placeholder(R.drawable.hero_placeholder)                                                                       //attesa del carimento, da cmabiare
                   .memoryPolicy(MemoryPolicy.NO_CACHE)
                   .networkPolicy(NetworkPolicy.NO_CACHE)
                   .resize(900, 600)
                   .centerCrop()
                   .into(imageView)

               AndroidView(
                   factory = { context ->
                       val imageView = ImageView(context).apply {
                           layoutParams = ViewGroup.LayoutParams(
                               ViewGroup.LayoutParams.MATCH_PARENT,
                               ViewGroup.LayoutParams.MATCH_PARENT
                           )
                       }

                       val imageUrl = selectedHeroThumbnail.replace("_", "/").replace("http://", "https://") + ".jpg"

                       Picasso.get()
                           .load(imageUrl)
                           .placeholder(R.drawable.hero_placeholder)
                           .memoryPolicy(MemoryPolicy.NO_CACHE)
                           .networkPolicy(NetworkPolicy.NO_CACHE)
                           .resize(900, 600)
                           .centerCrop()
                           .into(imageView)

                       imageView
                   },
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(12.dp)
                       .border(border = BorderStroke(width = 1.dp, Color.Black), shape = RectangleShape)
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

               FavouriteCheckbox(
                   isFavorite.value,
                   onCheckedChange = {
                       isFavorite.value = it
                       onFavoriteClicked(it)
                   }
               )

               Text(
                   text = stringResource(R.string.favorite).uppercase(),
                   fontSize = 20.sp,
                   fontFamily = fontFamily,
                   color = Color.White
               )
           }

           if (selectedHeroDescription == "DESCRIPTION NOT FOUND") {
               TextChip(stringResource(R.string.description_not_found), 15.sp, fontFamily)
           } else {
               TextChip("$selectedHeroDescription", 15.sp, fontFamily)
           }
           TextChip(stringResource(R.string.events)+" $selectedHeroEvents", 15.sp, fontFamily)
           TextChip(stringResource(R.string.stories) + " $selectedHeroStories", 15.sp, fontFamily)
           TextChip(stringResource(R.string.comics_explanation) + " $selectedHeroComics", 15.sp, fontFamily)

           Spacer(modifier = Modifier.height(10.dp))
       }
   }

    @Composable
    fun FavouriteCheckbox(
        isChecked: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {
        Row {
            IconButton(
                onClick = { onCheckedChange(!isChecked) }
            ) {
                Icon(
                    imageVector = if (isChecked) Icons.Default.Star else Icons.Default.Star,
                    contentDescription = if (isChecked) "Favourite" else "Not Favourite",
                    tint = if (isChecked) Color.Yellow else Color.White,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    }

}