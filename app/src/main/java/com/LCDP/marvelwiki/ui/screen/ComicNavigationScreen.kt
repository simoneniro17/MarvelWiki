package com.LCDP.marvelwiki.ui.screen

import android.app.Application
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.res.stringResource
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
import com.LCDP.marvelwiki.database.viewmodel.FavouriteComicViewModel
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

    //Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    //Creazione del comicsViewModel
    val comicsRepository = ComicsRepository()
    val comicsViewModel : ComicsViewModel = viewModel(factory = ComicViewModelFactory(comicsRepository, context.applicationContext as Application))

    //Stati delle checkbox per visualizzare solo preferiti o solo letti
    val checkedState1 = remember { mutableStateOf(false) }   //valore che filtra i fumetti preferiti
    val checkedState2 = remember { mutableStateOf(false) }   //valore che filtra i fumetti letti

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

            ComicSeparator(fontFamily = currentFont, comicsViewModel = comicsViewModel, checkedState1, checkedState2)
            ComicSearchScreen(navController = navController, comicsViewModel = comicsViewModel, fontFamily = currentFont, checkedState1, checkedState2)

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
            .padding(5.dp)
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                debouncer.debounce { onSearchQueryChange(it) }
            },
            label = {
                Text(
                    text = stringResource(R.string.search_comic_by_name).uppercase(),
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
            .padding(5.dp)
    ) {
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                debouncer.debounce { onSearchQueryChange(it) }
            },
            label = {
                Text(
                    text = stringResource(R.string.search_comic_by_isbn).uppercase(),
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
fun ComicSearchScreen(
    navController: NavController,
    comicsViewModel: ComicsViewModel,
    fontFamily: FontFamily,
    checkedState1 : MutableState<Boolean>,
    checkedState2 : MutableState<Boolean>
) {
    //Stati delle searchbar
    var searchQueryByName by remember { mutableStateOf("") }
    var searchQueryByISBN by remember {mutableStateOf("")}

    val showMessage = checkedState1.value && checkedState2.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ComicByNameSearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange = {
                searchQueryByName = it
                checkedState1.value = false
                checkedState2.value = false
                comicsViewModel.getComicByName(it)
            }
        )

        ComicByIsbnSearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange = {
                searchQueryByISBN = it // Update the search query
                checkedState1.value = false
                checkedState2.value = false
                if (searchQueryByISBN.length == 13) {
                    comicsViewModel.getComicsByIsbn(it)
                }

            }
        )

        if (showMessage) {
            DefaultComicList(
                navController = navController,
                fontFamily = fontFamily,
                context = LocalContext.current,
                comicsViewModel = comicsViewModel,
                text = stringResource(R.string.select_only_one_option).uppercase(),
            )
        } else {
            if (searchQueryByName.isNotEmpty() || searchQueryByISBN.isNotEmpty()) {
                AllComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel
                )
            } else if (checkedState1.value) {
                FavoriteComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel
                )

            } else if (checkedState2.value) {
                ReadComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel
                )

            } else {
                DefaultComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel,
                    text = stringResource(R.string.search_for_a_comic).uppercase(),
                )
            }
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
            .border(border = BorderStroke(width = (0.5).dp, color = Color.Black))
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp),
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
            text = stringResource(R.string.comics).uppercase(),
            fontSize = 30.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun ComicSeparator(
    fontFamily: FontFamily,
    comicsViewModel: ComicsViewModel,
    checkedState1 : MutableState<Boolean>,
    checkedState2 : MutableState<Boolean>
    ) {  //crea la barra sottostante alla searchbar contenente le spunte per filtrare la ricerca dei fumetti (preferiti e letti).
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color.Red)
            .border(border = BorderStroke((0.5).dp, Color.Black)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState1.value,
            onCheckedChange = { checkedState1.value = it},
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )
        // "id": 106565
        Text(
            stringResource(R.string.favorites).uppercase(),
            fontSize = 15.sp,
            fontFamily = fontFamily,
            color = Color.White
        )
        Checkbox(
            checked = checkedState2.value,
            onCheckedChange = { checkedState2.value = it },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Black
            )
        )
        Text(
            stringResource(R.string.read_comics).uppercase(),
            fontSize = 15.sp,
            fontFamily = fontFamily,
            color = Color.White
        )
    }
}

@Composable
fun AllComicList( //LISTA CONTENENTE RISULTATI DELLE RICERCE NELLE SEARCHBAR
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
fun DefaultComicList( // MESSAGGIO DI DEFAULT SE NON VIENE CERCATO NULLA
    navController : NavController,
    fontFamily : FontFamily,
    context : Context,
    comicsViewModel : ComicsViewModel,
    text: String
) {
    TextChip(text, 20.sp, fontFamily)
}

@Composable
fun FavoriteComicList( // LISTA CONTENENTE SOLO I PREFERITI
    navController : NavController,
    fontFamily : FontFamily,
    context : Context,
    comicsViewModel : ComicsViewModel
) {
    val listState = rememberLazyListState()
    comicsViewModel.loadFavouriteComics()
    val comicList = comicsViewModel.comicList

    LazyColumn(state = listState) {
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
fun ReadComicList( // LISTA CONTENENTE SOLO I FUMETTI LETTI
    navController : NavController,
    fontFamily : FontFamily,
    context : Context,
    comicsViewModel : ComicsViewModel
) {
    val listState = rememberLazyListState()
    comicsViewModel.loadReadComics()
    val comicList = comicsViewModel.comicList

    LazyColumn(state = listState) {
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

                    val selectedComicTitle = selectedComic.title
                    var selectedComicThumbnail = selectedComic.thumbnail?.path
                    selectedComicThumbnail = selectedComicThumbnail?.replace("/", "_")
                    var selectedComicDescription = selectedComic.description

                    if (selectedComicDescription.isNullOrEmpty()) {
                        selectedComicDescription = "DESCRIPTION NOT FOUND"
                    }

                    val selectedComicId = selectedComic.comicId

                    val args = listOf(
                        selectedComicTitle,
                        selectedComicThumbnail,
                        selectedComicDescription,
                        selectedComicId,
                        "NO"
                    )
                    navController.navigate("comicScreen/${args.joinToString("/")}")
                }
                ),
            verticalArrangement = Arrangement.Top
        ) {
            val imageView = remember { ImageView(context) }

            Picasso.get()
                .load((selectedComic.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                .placeholder(R.drawable.comic_placeholder)
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
                fontSize = 20.sp,
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
