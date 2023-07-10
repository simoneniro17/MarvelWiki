package com.LCDP.marvelwiki.ui.screen
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.HeroModel

//SCHERMATA HOME
@Composable
fun HomeScreen(navController : NavController) {

    //In questa classe e nella classe HeroScreen si fa largamente uso del metodo TextChip. E' un metodo composable che permette,
    // dati come argomenti un font, la dimensione del testo e il testo, di creare una piccola label contenente il testo inserito, automaticamente
    // centrata nello schermo. Le TextChip sono facilmente riusabili per aggiungere serie di informazioni su un personaggio.

    //Setup del font
    val marvelFont = FontFamily(Font(R.font.marvel_font, FontWeight.Thin))

    //TESTING ELEMENTS (utilizzati solo per prova, non coincidono col model definitivo dei personaggi, da eliminare)
    val hulk = HeroModel(1, "hulk", R.drawable.hulk, "Robert Bruce Banner", "Stan Lee", "1962", "The incredible hulk N.1", "...","...")
    val spiderman = HeroModel(2, "spider man", R.drawable.spiderman, "Peter Parker", "Stan Lee", "1962", "The Amazing Spiderman", "...", "...")

    //CREAZIONE DELLA SCHERMATA INTERA
    Box (modifier = Modifier
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
        Column (
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            HomeScreenUpperBar(marvelFont)                                               //Setup of the Layout Bar displaying Home
            SearchBar(marvelFont)                                                        //Setup of the search bar - top screen
            NavigationButtons(navController, marvelFont)                                 //Setup of navigation buttons - top screen
            DailyHeroBanner(marvelFont)                                                  //Setup of the "DAILY HERO" banner - mid screen
            DailyHeroCard(marvelFont, spiderman)                                         //Setup of the scrollable daily hero card (same card as the navigation ones) - bottom screen
        }
    }
}

@Composable
fun HomeScreenUpperBar(fontFamily : FontFamily) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.Red)
            .border(border = BorderStroke(width = 1.dp, color = Color.Black))
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "home".uppercase(),
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SearchBar(fontFamily : FontFamily) {
    var textFieldState by remember {
        mutableStateOf("")
    }
    Row (verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {textFieldState = it},
            label = {Text(
                text = "Search a character/film/comic".uppercase(),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = fontFamily
            )},
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.search_icon),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(30.dp,30.dp)
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
}                                                        //fontFamily needed for texts inside the search bar

@Composable
fun NavigationButtons (navController : NavController, fontFamily : FontFamily) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = { navController.navigate(Screens.NavigationScreen.route) }),
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painterResource(R.drawable.holo_globe),
                contentDescription = "NAVIGATION",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .width(150.dp)
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black)
                    )
            )

            Text(
                text = "NAVIGATION".uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color.Red)
                    .fillMaxHeight()
                    .width(150.dp)
                    .padding(vertical = 2.dp)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = { navController.navigate(Screens.FavoriteScreen.route) }),
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painterResource(R.drawable.avengers),
                contentDescription = "FAVORITES",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .width(150.dp)
                    .border(
                        border = BorderStroke(width = 1.dp, color = Color.Black),
                    )
            )

            Text(
                text = "FAVORITES".uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(color = Color.Red)
                    .fillMaxHeight()
                    .width(150.dp)
                    .padding(vertical = 2.dp)
            )

        }

    }
}                //fontFamily needed for texts inside of the buttons
//Nav controller needed for buttons to let you navigates to other screens

@Composable
fun DailyHeroBanner(fontFamily : FontFamily) {
    Spacer(modifier = Modifier.height(30.dp))
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(Color.Red)
        .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box (modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(1.dp, color = Color.Black)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "DAILY HERO",
                color = Color.White,
                fontSize = 40.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}                                                  //fontFamily needed for the "DAILY HERO" text

@Composable
fun DailyHeroCard(fontFamily : FontFamily, selectedHero : HeroModel) {
    Row {
        Spacer(modifier = Modifier.height(30.dp))
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            TextChip(selectedHero.name, 50.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                ImageCard(
                    painterResource(selectedHero.heroPic),
                    contentDescription = "None",
                    modifier = Modifier.fillMaxSize()
                )
            }

            TextChip("[Hero name] : " + selectedHero.name, 20.sp, fontFamily)

            TextChip("[Real name]: " + selectedHero.realName, 20.sp, fontFamily)

            TextChip("[Creator]: " + selectedHero.creator, 20.sp, fontFamily)

            TextChip("[First Appearance]: " + selectedHero.creationDate + " " + selectedHero.firstAppearance, 20.sp, fontFamily)

            TextChip("[Info]: " + selectedHero.info, 20.sp, fontFamily)

            TextChip("[Origins]: " + selectedHero.origins, 20.sp, fontFamily)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color.Red)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black))
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("")
            }

        }
    }

}                                                    //fontFamily needed for every information's text
//TextChip method allows you to create a CHIP with custom text, size, font
@Composable
fun ImageCard(
    painter : Painter,
    contentDescription : String,
    modifier : Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .border(
                border = BorderStroke(width = 2.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Image(modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}                                                                                             //Creates an image card. Painter must be an INT, contentDescription can be "none", do no specify modifier

@Composable
fun TextChip(text : String, fontSize : TextUnit, fontFamily : FontFamily) {

    val text = text

    Spacer(modifier = Modifier.height(10.dp))

    Box (modifier = Modifier
        .padding(10.dp)
        .border(border = BorderStroke(width = 1.dp, Color.Black), shape = RoundedCornerShape(10.dp))
        .clip(shape = RoundedCornerShape(10.dp))
        .background(Color.Gray),
        contentAlignment = Alignment.Center) {
        Text(
            text = text.uppercase(),
            color = Color.White,
            fontSize = fontSize,
            fontFamily = fontFamily,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .background(Color.Black)
                .padding(10.dp)
        )
    }

}