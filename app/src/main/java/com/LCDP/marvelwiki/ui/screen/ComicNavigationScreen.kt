package com.LCDP.marvelwiki.ui.screen

import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.LCDP.marvelwiki.data.model.Comic
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.ComicViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.usefulStuff.Debouncer
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

//  Schermata di navigazione dei fumetti
@Composable
fun ComicNavigationScreen(navController: NavController, context: Context) {

    //  Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    //  Setup stato delle checkbox per i preferiti e per i letti
    val favState = remember { mutableStateOf(false) }
    val readState = remember { mutableStateOf(false) }

    // Setup delle stringhe
    val filterByNameMsg = stringResource(R.string.name_filter_active)
    val filterByISBNMsg = stringResource(R.string.isbn_filter_active)

    //  Inizializzazione del repository e del ViewModel per i Comic
    val comicsRepository = ComicsRepository()
    val comicsViewModel: ComicsViewModel =
        viewModel(
            factory = ComicViewModelFactory(
                comicsRepository = comicsRepository,
                context.applicationContext as Application
            )
        )

    //  Schermata principale con sfondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Black)
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.bg_prova),
            contentDescription = "none",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
        //  Colonna per contenere tutti gli elementi della schermata
        //  NOTA: ai NavigationButtons passare il navController per switchare schermata al click
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            //  Barra superiore con opzioni di navigazione
            ComicNavigationScreenUpperBar(
                navController,
                currentFont,
                favState,
                readState,
                comicsViewModel
            )

            //  Separatore per filtrare i fumetti letti e preferiti
            /*ComicSeparator(
                fontFamily = currentFont,
                comicsViewModel = comicsViewModel,
                favState,
                readState
            )*/

            //  Area per la ricerca dei fumetti
            ComicSearchScreen(
                navController = navController,
                comicsViewModel = comicsViewModel,
                fontFamily = currentFont,
                favState,
                readState,
                context,
                filterByNameMsg,
                filterByISBNMsg
            )
        }
    }
}

//  Barra superiore
@Composable
fun ComicNavigationScreenUpperBar(navController: NavController, fontFamily: FontFamily,
                                  favState: MutableState<Boolean>, readState: MutableState<Boolean>,
                                  comicsViewModel: ComicsViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
            .border(border = BorderStroke(width = (0.5).dp, color = Color.Black))
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //  Icona per tornare alla HomeScreen
        Column(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .border(border = BorderStroke(2.dp, color = Color.Black), shape = CircleShape)
                .clip(shape = CircleShape)
                .background(Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally
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

        Spacer(
            modifier = Modifier
                .width(50.dp)
                .fillMaxHeight()
        )

        //  Testo nella barra superiore
        Text(
            text = stringResource(R.string.comics).uppercase(),
            fontSize = 25.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .width(20.dp)
                .fillMaxHeight()
        )

        //  "Checkbox" per il filtro dei preferiti
        FavouriteCheckbox(
            isChecked = favState.value,
            onCheckedChange = {
                favState.value = it
            }
        )

        Spacer(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
        )

        // "Checkbox per il filtro dei fumetti letti
        ReadComicCheckbox(
            isRead = readState.value,
            onReadChange = {
                readState.value = it
            }
        )
    }
}

//  Barra sottostante alla SearchBar contenente filtri per la ricerca dei fumetti
@Composable
fun ComicSeparator(fontFamily: FontFamily, comicsViewModel: ComicsViewModel,
                   favState: MutableState<Boolean>, readState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Transparent)
            .border(border = BorderStroke((0.5).dp, Color.Black)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        //  "Checkbox" per il filtro dei preferiti
        FavouriteCheckbox(
            isChecked = favState.value,
            onCheckedChange = {
                favState.value = it
            }
        )

        // Testo che segue la checkbox dei fumetti preferiti
        Text(
            stringResource(R.string.favorites).uppercase(),
            fontSize = 15.sp,
            fontFamily = fontFamily,
            color = Color.White
        )

        //Spacer per distinguere bene i pulsanti
        Column(
            modifier = Modifier
                .width(20.dp)
                .fillMaxHeight()
                .background(color = Color.Transparent)
        ) {}

        // Checkbox per il filtro dei fumetti letti
        ReadComicCheckbox(
            isRead = readState.value,
            onReadChange = {
                readState.value = it
            }
        )

        // Testo che segue la checkbox dei fumetti letti
        Text(
            stringResource(R.string.read_comics).uppercase(),
            fontSize = 15.sp,
            fontFamily = fontFamily,
            color = Color.White
        )
    }
}

//  Schermata per la ricerca dei fumetti
@Composable
fun ComicSearchScreen(navController: NavController, comicsViewModel: ComicsViewModel,
                      fontFamily: FontFamily, favState: MutableState<Boolean>,
                      readState: MutableState<Boolean>, context : Context,
                      filterByNameMsg : String,
                      filterByISBNMsg : String) {

    //  Stato di visualizzazione della barra di ricerca per nome
    var isNameSearchVisible by remember { mutableStateOf(true) }

    //  Stato di visualizzazione della barra di ricerca per ISBN
    var isIsbnSearchVisible by remember { mutableStateOf(false) }

    //  Stati delle searchbar
    var searchQueryByName by remember { mutableStateOf("") }
    var searchQueryByISBN by remember { mutableStateOf("") }

    //  Se entrambi i filtri sono attivi, deve essere mostrato un messaggio
    val showMessage = favState.value && readState.value

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        //  Barra di ricerca dei fumetti per nome
        if (isNameSearchVisible) {
            ComicByNameSearchBar(
                fontFamily = fontFamily,
                onSearchQueryChange = {
                    searchQueryByName = it
                    favState.value = false
                    readState.value = false
                    comicsViewModel.getComicByName(it)
                }
            )
        }

        //  Barra di ricerca di un fumetto per codice ISBN
        if (isIsbnSearchVisible) {
            ComicByIsbnSearchBar(
                fontFamily = fontFamily,
                onSearchQueryChange = {
                    searchQueryByISBN = it // Update the search query
                    favState.value = false
                    readState.value = false
                    if (searchQueryByISBN.length == 13) {
                        comicsViewModel.getComicsByIsbn(it)
                    }

                }
            )
        }

        //  Pulsante per passare dalla barra di ricerca per nome a quella per ISBN e viceversa
        IconButton(
            onClick = {
                isNameSearchVisible = !isNameSearchVisible
                isIsbnSearchVisible = !isIsbnSearchVisible

                // Toast che mostra all' utente qual' è il filtro che ha impostato (per nome o per ISB) nella ricerca dei fumetti
                if (isNameSearchVisible) {
                    Toast.makeText(context, filterByNameMsg, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, filterByISBNMsg, Toast.LENGTH_SHORT).show()
                }

            },
        ) {
            Icon(
                painter = if (!isNameSearchVisible) painterResource(id = R.drawable.title) else painterResource(
                    id = R.drawable.numeric
                ),
                contentDescription = if (isNameSearchVisible) "Title" else "ISBN",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )
        }
    }
        //  Mostra messaggio se entrambi i filtri sono attivi
        if (showMessage) {
            DefaultComicList(
                fontFamily = fontFamily,
                text = stringResource(R.string.select_only_one_option).uppercase(),
            )
        } else {
            //  Mostra l'elenco dei fumetti in base alle query di ricerca o ai filtri
            if (searchQueryByName.isNotEmpty() || searchQueryByISBN.isNotEmpty()) {
                //  Fumetto per nome o per ISBN
                AllComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel
                )
            } else if (favState.value) {
                //  Fumetti preferiti
                FilterComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel,
                    type = "favourite"
                )
            } else if (readState.value) {
                //  Fumetti letti
                FilterComicList(
                    navController = navController,
                    fontFamily = fontFamily,
                    context = LocalContext.current,
                    comicsViewModel = comicsViewModel,
                    type = "read"
                )
            } else {
                //  Messaggio di base per aiutare l'utente
                DefaultComicList(
                    fontFamily = fontFamily,
                    text = stringResource(R.string.search_for_a_comic).uppercase(),
                )
            }
        }
    Spacer(modifier = Modifier.fillMaxHeight()
        .width(20.dp))
    }

//  Ricerca fumetti per titolo
@Composable
fun ComicByNameSearchBar(fontFamily: FontFamily, onSearchQueryChange: (String) -> Unit) {

    //  Stato del testo nella barra di ricerca
    var textFieldState by remember { mutableStateOf("") }

    //  Debouncer per gestire la ricerca ritardata in base all'input dell'utente
    val debouncer = remember { Debouncer(400) }

    //  Creazione della barra di ricerca
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding( horizontal = (0.5).dp,vertical = 10.dp)
    ) {
        //  Campo di testo con icona di ricerca
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
                focusedIndicatorColor = Color.Black,
                leadingIconColor = Color.White,
                cursorColor = Color.White,
                textColor = Color.White,
                backgroundColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f),
        )
    }
}

//  Ricerca fumetti per codice ISBN
@Composable
fun ComicByIsbnSearchBar(fontFamily: FontFamily, onSearchQueryChange: (String) -> Unit) {

    //  Stato del testo nella barra di ricerca
    var textFieldState by remember { mutableStateOf("") }

    //  Debouncer per gestire la ricerca ritardata in base all'input dell'utente
    val debouncer = remember { Debouncer(400) }

    //  Creazione della barra di ricerca
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding( horizontal = (0.5).dp,vertical = 10.dp)
    ) {
        //  Campo di testo con icona di ricerca
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
                focusedIndicatorColor = Color.Black,
                leadingIconColor = Color.White,
                cursorColor = Color.White,
                textColor = Color.White,
                backgroundColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f)

        )
    }
}

//  Lista dei fumetti in base alla ricerca nelle searchbar
@Composable
fun AllComicList(navController: NavController, fontFamily: FontFamily,
                 context: Context, comicsViewModel: ComicsViewModel) {

    //  Stato di scorrimento della lazyColumn
    val listState = rememberLazyListState()

    //  Viene presa la lista dal ViewModel
    val comicList = comicsViewModel.comicList

    //  Caricamento degli elementi della lista appena presa
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

//  Thumbnail relativa ad un fumetto
@Composable
fun ComicThumbnail(navController: NavController, fontFamily: FontFamily,
                   selectedComic: Comic, context: Context) {

    //  Riga che contiene il composable dell'immagine e il nome del fumetto
    Row(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //  Colonna che contiene l'immagine del fumetto e il suo nome
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable(onClick = {

                    //  Estrazione dei dettagli del fumetto selezionato
                    val selectedComicTitle = selectedComic.title
                    var selectedComicThumbnail = selectedComic.thumbnail?.path
                    var selectedComicDescription = selectedComic.description
                    val selectedComicId = selectedComic.comicId
                    var selectedComicIsbn = selectedComic.isbn
                    val selectedComicPageCount = selectedComic.pageCount
                    val selectedComicSeries = selectedComic.series?.name

                    if (selectedComicDescription.isNullOrEmpty()) {
                        selectedComicDescription = "NOT AVAILABLE"
                    }

                    if (selectedComicIsbn.isNullOrEmpty()) {
                        selectedComicIsbn = "NOT AVAILABLE"
                    }

                    if (selectedComicSeries.isNullOrEmpty()) {
                        selectedComicIsbn = "NOT AVAILABLE"
                    }

                    selectedComicThumbnail = selectedComicThumbnail?.replace("/", "_")

                    //  Creazione degli argomenti per la navigazione alla schermata del fumetto
                    val args = listOf(
                        selectedComicTitle, selectedComicThumbnail, selectedComicDescription,
                        selectedComicId, "NO", selectedComicIsbn, selectedComicPageCount,
                        selectedComicSeries
                    )

                    navController.navigate("comicScreen/${args.joinToString("/")}")
                }),
            verticalArrangement = Arrangement.Top
        ) {

            //  ImageView per mostrare l'immagine del fumetto
            val imageView = remember { ImageView(context) }

            //  Caricmaneto dell'immagine del fumetto utilizzando Picasso
            Picasso.get()
                .load((selectedComic.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                .placeholder(R.drawable.comic_placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(510, 310)
                .centerCrop()
                .into(imageView)

            //  Rappresentazione dell'immagine come Composable AndroidView
            AndroidView(
                factory = { imageView },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            )

            //  Testo con il nome del fumetto sopra l'immagine
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

//  Messaggio di default quando si apre la ComicNavigationScreen
@Composable
fun DefaultComicList(fontFamily: FontFamily, text: String) {
    TextChip(text, 15.sp, fontFamily)
}

//  Per filtrare i fumetti tra letti e preferiti
@Composable
fun FilterComicList(navController: NavController, fontFamily: FontFamily,
                  context: Context, comicsViewModel: ComicsViewModel, type: String) {

    //  Stato di scorrimento della lazyColumn
    val listState = rememberLazyListState()

    //  Viene presa la lista dal ViewModel
    if(type == "favourite")
        comicsViewModel.loadFavouriteComics()
    else
        comicsViewModel.loadReadComics()

    val comicList = comicsViewModel.comicList

    //  Caricamento degli elementi della lista appena presa
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