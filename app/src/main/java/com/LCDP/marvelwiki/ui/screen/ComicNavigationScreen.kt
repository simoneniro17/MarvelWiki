package com.LCDP.marvelwiki.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.HeroModel

@Composable
fun ComicNavigationScreen(navController: NavController) {

    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))

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
            //AllComicsList(navController, marvelFont, heroList)

        }
    }
}

@Composable
fun ComicSearchBar(fontFamily: FontFamily) {
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
fun ComicNavigationScreenUpperBar(navController: NavController, fontFamily: FontFamily) {
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
            text = "COMICS".uppercase(),
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun ComicSeparator(fontFamily: FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.Red)
            .border(border = BorderStroke(1.dp, Color.Black)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState1 = remember { mutableStateOf(false) }
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
        val checkedState2 = remember { mutableStateOf(false) }
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

/*@Composable
fun AllComicsList(navController: NavController, fontFamily: FontFamily, heroList: List<HeroModel>) {
    LazyColumn {
        items(3) {                      //Il numero 3 è provvisorio e per testing, andrà sostituito col numero esatto di personaggi totali
            HeroThumbnail(
                navController,
                fontFamily,
                heroList[it]
            )      //Questo metodo costruisce (per ogni entry [it] della lista) un' immagine cliccabile del personaggio che si vuole approfondire
        }
    }
}
 */