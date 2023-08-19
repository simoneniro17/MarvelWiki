package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.Comic
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.ComicViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.usefulStuff.Debouncer
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

//NOTA: non ho commentato le parti relative esclusivamente al layout e altri fattori grafici puramente estetici che non implementano alcuna funzionalità.
@Composable
fun ComicNavigationScreen(navController: NavController, context : Context) {

    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    val comicsRepository = ComicsRepository()
    val comicsViewModel : ComicsViewModel = viewModel(factory = ComicViewModelFactory(comicsRepository))

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
                currentFont
            )             //Creazione del layout esterno alla lazy list (la barra fissa in alto)

            //ComicSeparator(currentFont)
            ComicByNameSearchScreen(navController = navController, comicsViewModel = comicsViewModel, fontFamily = currentFont)
            ComicByIsbnSearchScreen(navController = navController, comicsViewModel = comicsViewModel, fontFamily = currentFont)
        }
    }
}

@Composable
fun ComicByIsbnSearchBar(
    fontFamily: FontFamily,
    onSearchQueryChange: (String) -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }
    val debouncer = remember { Debouncer(400) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                debouncer.debounce { onSearchQueryChange(it) }
            },
            label = {
                Text(
                    text = "Inserisci l'Isbn del tuo comic".uppercase(),
                    color = Color.White,
                    fontSize = 15.sp,
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
}

@Composable
fun ComicByIsbnSearchScreen(
    navController: NavController,
    comicsViewModel: ComicsViewModel,
    fontFamily: FontFamily
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ComicByIsbnSearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange = {
                searchQuery = it // Update the search query
                if(searchQuery.length == 13){
                    comicsViewModel.getComicsByIsbn(it)
                }
            }
        )
        if (searchQuery.isNotEmpty()) {
            AllComicList(
                navController = navController,
                fontFamily = fontFamily,
                context = LocalContext.current,
                comicsViewModel = comicsViewModel
            )
        }
    }
}




@Composable
fun ComicByNameSearchBar(
    fontFamily: FontFamily,
    onSearchQueryChange: (String) -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }
    val debouncer = remember { Debouncer(400) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                debouncer.debounce { onSearchQueryChange(it) }
            },
            label = {
                Text(
                    text = "Search a comic".uppercase(),
                    color = Color.White,
                    fontSize = 15.sp,
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
}

@Composable
fun ComicByNameSearchScreen(
    navController: NavController,
    comicsViewModel: ComicsViewModel,
    fontFamily: FontFamily
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ComicByNameSearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange = {
                    searchQuery = it // Update the search query
                    comicsViewModel.getComicByName(it)

            }
        )
        if (searchQuery.isNotEmpty()) {
            AllComicList(
                navController = navController,
                fontFamily = fontFamily,
                context = LocalContext.current,
                comicsViewModel = comicsViewModel
            )
        }
    }
}

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
fun AllComicList(
    navController: NavController,
    fontFamily: FontFamily,
    context: Context,
    comicsViewModel: ComicsViewModel

) {
    // listState utilizzata per avere un controllo preciso sullo stato di scorrimento della lazyColumn
    val listState = rememberLazyListState()
    val comicList = comicsViewModel.comicList       //prendo dal viewModel la characterList caricata che, alla prima apertura contiene solo i primi 100 eroi

    LazyColumn(state = listState) {                                  //ora carichiamo gli elementi della lista
        items(comicList.size) { index ->
            ComicThumbnail(
                navController,
                fontFamily,
                comicList[index],
                context
            )
        }
    }
}

@Composable
fun ComicThumbnail(
    navController: NavController,
    fontFamily: FontFamily,
    selectedComic: Comic,
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
                .load((selectedComic.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
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
                text = selectedComic.title!!.uppercase(),
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
