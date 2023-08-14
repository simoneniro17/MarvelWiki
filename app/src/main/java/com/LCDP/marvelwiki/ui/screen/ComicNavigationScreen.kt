package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.HeroModel
import com.LCDP.marvelwiki.printer.retrieveLatestComicId
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

//NOTA: non ho commentato le parti relative esclusivamente al layout e altri fattori grafici puramente estetici che non implementano alcuna funzionalità.
@Composable
fun ComicNavigationScreen(navController: NavController, context : Context) {

    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))

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
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            ComicNavigationScreenUpperBar(
                navController,
                marvelFont
            )             //Creazione del layout esterno alla lazy list (la barra fissa in alto)
            ComicSearchBar(marvelFont)
            ComicSeparator(marvelFont)
            //AllComicsList(navController, marvelFont, retrieveLatestComicId(), context = context)  //chiama la stessa funzione che genera la lista degli eroi per testare il ComicScreen. DA MODIFICARE.

        }
    }
}

@Composable
fun ComicSearchBar(fontFamily: FontFamily) {    //Crea la search bar per la ricerca dei fumetti
    var textFieldState by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = { textFieldState = it },
            label = {
                Text(
                    text = "Search a comic".uppercase(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = fontFamily
                )
            },
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.search_icon),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(30.dp, 30.dp)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = Color.Red,
                leadingIconColor = Color.White,
                cursorColor = Color.Red,
                textColor = Color.White
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
} //fontFamily needed for texts inside the search bar


@Composable
fun ComicNavigationScreenUpperBar(navController: NavController, fontFamily: FontFamily) { //crea la barra superiore contenente il pulsante per tornare alla home.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
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
                painterResource(R.drawable.back_arrow),   //bottone per tornare indietro
                contentDescription = "HOME",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .clickable(onClick = { navController.navigate(Screens.HomeScreen.route) })
            )
        }

        Text(
            text = "COMICS".uppercase(),
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun ComicSeparator(fontFamily: FontFamily) {  //crea la barra sottostante alla searchbar contenente le spunte per filtrare la ricerca dei fumetti (preferiti e letti).
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.Red)
            .border(border = BorderStroke(1.dp, Color.Black)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState1 = remember { mutableStateOf(false) }   //valore che filtra i fumetti preferiti
        Checkbox(
            checked = checkedState1.value,
            onCheckedChange = { checkedState1.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )

        Text(
            "Favorite comics".uppercase(),
            fontSize = 20.sp,
            fontFamily = fontFamily,
            color = Color.White
        )
        val checkedState2 = remember { mutableStateOf(false) }  //valore che filtra i fumetti letti.
        Checkbox(
            checked = checkedState2.value,
            onCheckedChange = { checkedState2.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )

        Text(
            "Read comics".uppercase(),
            fontSize = 20.sp,
            fontFamily = fontFamily,
            color = Color.White
        )
    }
}

@Composable
fun AllComicsList(                                 //Ho usato la lo stesso metodo per la lista degli eroi solo per testare il ComicScreen. DA MODIFICARE.
    navController: NavController,
    fontFamily: FontFamily,
    characterList: List<Character>?,
    context: Context
) {
    LazyColumn {
        items(100) {                      //Il numero 3 è provvisorio e per testing, andrà sostituito col numero esatto di personaggi totali
            if (characterList != null) {
                ComicThumbnail(
                    navController,
                    fontFamily,
                    characterList[it],
                    context
                )
            }      //Questo metodo costruisce (per ogni entry [it] della lista) un' immagine cliccabile del personaggio che si vuole approfondire
        }
    }
}

@Composable
fun ComicThumbnail(
    navController: NavController,
    fontFamily: FontFamily,
    selectedHero: Character,
    context: Context
) {

    Row(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = {
                    navController.navigate(Screens.ComicScreen.route)
                }
                ),
            verticalArrangement = Arrangement.Top
        ) {
            val imageView = remember { ImageView(context) }

            Picasso.get()
                .load((selectedHero.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(510, 310)
                .centerCrop()
                .into(imageView)

            AndroidView(
                factory = { imageView },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            )

            Text(
                text = selectedHero.name!!.uppercase(),
                fontSize = 30.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color.Red)
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }

    }
}
