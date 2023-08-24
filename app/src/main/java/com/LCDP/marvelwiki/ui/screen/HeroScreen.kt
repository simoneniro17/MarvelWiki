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
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.viewmodel.FavouriteCharacterViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso


@Composable
fun HeroScreen(navController: NavController, arguments: List<String>, context: Context) {

    val selectedHeroName = arguments[0]
    val selectedHeroThumbnail = arguments[1]
    val selectedHeroDescription = arguments[2]
    val selectedHeroId = arguments[3]

    val appDatabase = appDatabase.getDatabase(context)
    val databaseAccess = DatabaseAccess(appDatabase)
    val favouriteCharacterViewModel = FavouriteCharacterViewModel(databaseAccess)

   //Setup del font
   val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

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

           HeroScreenUpperBar(
               navController,
               currentFont,
               selectedHeroName
           )       //Costruzione della barra superiore (il navController è stato passato perchè la barra in questione contiene un tasto per tornare alla schermata di navigazione)
          HeroCard(
               currentFont,
               selectedHeroThumbnail,
               selectedHeroDescription,
               context,
               onFavoriteClicked = {isFavorite ->
                   if(isFavorite){
                       favouriteCharacterViewModel.insertData(FavouriteCharacter(selectedHeroId))
                   } else {
                       favouriteCharacterViewModel.deleteData(FavouriteCharacter(selectedHeroId))
                   }
               }
           )                     //Metodo riusabile che, se fornito di un model eroe (che dovrà essere modificato in base alle info fornite dall' API), costruisce automaticamente la sua pagina)
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
       horizontalArrangement = Arrangement.spacedBy(80.dp),
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
    context: Context,
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
                   .placeholder(R.drawable.avengers_logo)                                                                            //attesa del carimento, da cmabiare
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
               val checkedState = remember { mutableStateOf(false) }  //La variabile checkedState tiene conto se l'ero è preferito o meno.
               Checkbox(
                   checked = checkedState.value,
                   onCheckedChange = { checkedState.value = it
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
           }

           TextChip(selectedHeroDescription, 15.sp, fontFamily)

           Spacer(modifier = Modifier.height(10.dp))
       }
   }
}