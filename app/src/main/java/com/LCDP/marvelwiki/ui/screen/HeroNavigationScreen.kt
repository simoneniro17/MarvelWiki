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
import com.LCDP.marvelwiki.printer.retrieveCharacterList
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

@Composable
fun NavigationScreen(navController: NavController,context: Context) {

    //Setup del font
    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))
/*
    //TESTING ELEMENTS (La lista è fittizia e contiene elementi provvisori e da eliminare. Volendo si può riusare la stessa lista che dovrà contenere TUTTI gli eroi dal database, in modo da sfruttare al meglio la lazy list che ho implementato)
    val hulk = HeroModel(
        1,
        "hulk",
        R.drawable.hulk,
        "Robert Bruce Banner",
        "Stan Lee",
        "1962",
        "The incredible hulk N.1",
        "...",
        "..."
    )
    val spiderman = HeroModel(
        2,
        "spider man",
        R.drawable.spiderman,
        "Peter Parker",
        "Stan Lee",
        "1962",
        "The Amazing Spiderman",
        "...",
        "..."
    )
    val ironman = HeroModel(
        3,
        "Iron Man",
        R.drawable.ironman,
        "Tony Stark",
        "stan lee",
        "1962",
        "Tales of suspance",
        "..",
        "..."
    )
    val heroList = ArrayList<HeroModel>(10000)
    heroList.add(hulk)
    heroList.add(spiderman)
    heroList.add(ironman)
*/
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.background),
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
            NavigationScreenUpperBar(
                navController,
                marvelFont
            )             //Creazione del layout esterno alla lazy list (la barra fissa in alto)
            SearchBar(marvelFont)
            //FavoriteFilterButton(marvelFont, false)
            Separator(marvelFont)
            AllHeroesList(navController, marvelFont, retrieveCharacterList(), context = context)
        }
    }
}

    @Composable
    fun NavigationScreenUpperBar(navController: NavController, fontFamily: FontFamily) {
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
                        .clickable(onClick = { navController.navigate(Screens.HomeScreen.route) })
                )
            }

            Text(
                text = "HEROES".uppercase(),
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
            )

        }
    }

    @Composable
    fun SearchBar(fontFamily: FontFamily) {
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
                        text = "Search a character".uppercase(),
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
                    leadingIconColor = Color.Black,
                    cursorColor = Color.Red,
                    textColor = Color.White
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    } //fontFamily needed for texts inside the search bar

    @Composable
    fun FavoriteFilterButton(fontFamily: FontFamily, clicked: Boolean) {

        val currentColor: Color
        if (clicked) {
            currentColor = Color.Red
        } else {
            currentColor = Color.LightGray
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(color = currentColor)
                .border(border = BorderStroke(1.dp, Color.Black))
                .clickable(onClick = { !clicked })
        ) {

        }
    }

    @Composable
    fun Separator(fontFamily: FontFamily) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color.Red)
                .border(border = BorderStroke(1.dp, Color.Black)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val checkedState = remember { mutableStateOf(false) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Black,
                    //checkmarkColor = Color.Black
                )
            )

            Text(
                "Visualize only favorite heroes".uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White
            )
        }
    }

    @Composable
    fun AllHeroesList(
        navController: NavController,
        fontFamily: FontFamily,
        characterList: List<Character>?,
    context: Context
    ) {
        LazyColumn {
            items(100) {                      //Il numero 3 è provvisorio e per testing, andrà sostituito col numero esatto di personaggi totali
                if (characterList != null) {
                    HeroThumbnail(
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
    fun HeroThumbnail(
        navController: NavController,
        fontFamily: FontFamily,
        selectedHero: Character,
    context: Context
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.9f)
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable(onClick = { navController.navigate(Screens.HeroScreen.route) }),
                verticalArrangement = Arrangement.Top
            ) {
                val imageView = remember { ImageView(context) }

                Picasso.get()
                    .load((selectedHero.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .resize(300, 300)
                    .centerCrop()
                    .into(imageView)

                AndroidView(
                    factory = { imageView },
                    modifier = Modifier.fillMaxSize()
                )

                selectedHero.name?.let {
                    Text(
                        text = it.uppercase(),
                        fontSize = 30.sp,
                        fontFamily = fontFamily,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(color = Color.Red)
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }

            }
        }
    }
