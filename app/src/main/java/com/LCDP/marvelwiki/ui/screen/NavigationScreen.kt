package com.LCDP.marvelwiki.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.HeroModel

@Composable
fun NavigationScreen(navController : NavController) {

    //Setup del font
    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))

    //TESTING ELEMENTS (La lista è fittizia e contiene elementi provvisori e da eliminare. Volendo si può riusare la stessa lista che dovrà contenere TUTTI gli eroi dal database, in modo da sfruttare al meglio la lazy list che ho implementato)
    val hulk = HeroModel(1, "hulk", R.drawable.hulk, "Robert Bruce Banner", "Stan Lee", "1962", "The incredible hulk N.1", "...","...")
    val spiderman = HeroModel(2, "spider man", R.drawable.spiderman, "Peter Parker", "Stan Lee", "1962", "The Amazing Spiderman", "...", "...")
    val ironman = HeroModel(3, "Iron Man", R.drawable.ironman, "Tony Stark", "stan lee", "1962", "Tales of suspance", "..", "...")
    val heroList = ArrayList<HeroModel>(10000)
    heroList.add(hulk)
    heroList.add(spiderman)
    heroList.add(ironman)

    Column (modifier = Modifier
        .background(Color.DarkGray)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        NavigationScreenUpperBar(navController, marvelFont)             //Creazione del layout esterno alla lazy list (la barra fissa in alto)
        AllHeroesList(navController, marvelFont, heroList)              //Lazy list contenente tutti i personaggi dal DB. E' necessario passargli anche il navController perchè se nella lista si sceglie di cliccare un personaggio, esso sarà attivato per condurre alla pagina relativa al personaggio selezionato)
    }
}

@Composable
fun NavigationScreenUpperBar(navController : NavController, fontFamily : FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "navigation".uppercase(),
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

        Column(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .border(border = BorderStroke(2.dp, color = Color.Black), shape = CircleShape)
                .clip(shape = CircleShape)
                .background(Color.Green)
        ) {
            Image(
                painterResource(R.drawable.avengers_tower),
                contentDescription = "HOME",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        border = BorderStroke(width = 1.dp, Color.Black),
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .clickable(onClick = {navController.navigate(Screens.HomeScreen.route)})
            )
        }

    }
}

@Composable
fun AllHeroesList(navController : NavController, fontFamily : FontFamily, heroList : List<HeroModel>) {
    LazyColumn {
        items(3) {                      //Il numero 3 è provvisorio e per testing, andrà sostituito col numero esatto di personaggi totali
            HeroThumbnail(navController, fontFamily, heroList[it])      //Questo metodo costruisce (per ogni entry [it] della lista) un' immagine cliccabile del personaggio che si vuole approfondire
        }
    }
}

@Composable
fun HeroThumbnail(navController : NavController, fontFamily : FontFamily, selectedHero : HeroModel) {

    Row (modifier = Modifier
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

            Image(
                painterResource(selectedHero.heroPic),
                contentDescription = "hero_pic",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black),
                    )
            )

            Text(
                text = selectedHero.name.uppercase(),
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