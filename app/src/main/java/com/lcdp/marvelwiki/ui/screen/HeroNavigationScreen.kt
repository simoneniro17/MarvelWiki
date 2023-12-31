package com.lcdp.marvelwiki.ui.screen

import android.app.Application
import android.content.Context
import android.widget.ImageView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.lcdp.marvelwiki.R
import com.lcdp.marvelwiki.data.model.Character
import com.lcdp.marvelwiki.data.repository.CharactersRepository
import com.lcdp.marvelwiki.ui.viewmodel.CharactersViewModel
import com.lcdp.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.lcdp.marvelwiki.util.Debouncer
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

//  Schermata di navigazione degli eroi
@Composable
fun NavigationScreen(navController: NavController, context: Context) {

    //  Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    //  Setup stato della checkbox per i preferiti
    val checkedState = remember { mutableStateOf(false) }

    //  Inizializzazione del repository e del ViewModel per i Character
    val charactersRepository = CharactersRepository()
    val charactersViewModel: CharactersViewModel =
        viewModel(
            factory = CharactersViewModelFactory(
                charactersRepository = charactersRepository,
                context.applicationContext as Application
            )
        )


    //  Caricamento iniziale della lista dei primi 100 personaggi
    LaunchedEffect(key1 = Unit) {
        if (charactersViewModel.characterList.isEmpty()) {
            charactersViewModel.loadCharacterList()
        }
    }

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
            //  Barra superiore con opzioni di navigazione e filtro dei preferiti
            NavigationScreenUpperBar(
                navController,
                currentFont,
                checkedState,
                charactersViewModel
            )

            //  Area per la ricerca e per la visualizzazione della lista degli eori
            SearchScreen(
                navController = navController,
                charactersViewModel = charactersViewModel,
                fontFamily = currentFont,
                checkedState
            )
        }
    }
}

//  Barra superiore
@Composable
fun NavigationScreenUpperBar(navController: NavController, fontFamily: FontFamily, checkedState: MutableState<Boolean>, charactersViewModel: CharactersViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Red.copy(alpha = 0.55f))
            .border(border = BorderStroke(width = (0.5).dp, color = Color.Black))
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //  Pulsante per tornare alla HomeScreen
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

        //  Testo nella barra superiore
        Text(
            text = stringResource(R.string.heroes).uppercase(),
            fontSize = 25.sp,
            color = Color.White,
            fontFamily = fontFamily,
            textAlign = TextAlign.Center
        )

        //  "Checkbox" per il filtro dei preferiti
        FavouriteCheckbox(
            isChecked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                if (!it) {
                    charactersViewModel.unloadFavouriteCharacters()
                } else {
                    charactersViewModel.loadFavouriteCharacters()
                }
            }
        )
    }
}

//  Barra di ricerca
@Composable
fun SearchBar(fontFamily: FontFamily, onSearchQueryChange: (String) -> Unit, checkedState: MutableState<Boolean>) {

    //  Stato locale per il valore dell'input della barra di ricerca
    var textFieldState by remember { mutableStateOf("") }

    //  Debouncer per gestire la ricerca ritardata in base all'input dell'utente
    val debouncer = remember { Debouncer(300) }

    //  Creazione della barra di ricerca
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 0.dp)
    ) {
        //  Campo di testo con icona di ricerca
        OutlinedTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                //  Utilizzo del debouncer per ritardare la ricerca
                debouncer.debounce { onSearchQueryChange(it) }
            },
            label = {
                Text(
                    text = stringResource(R.string.search_a_character).uppercase(),
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

    //  Se il campo di ricerca non è vuoto, il filtro dei preferiti viene disabilitato
    if (textFieldState.isNotEmpty()) {
        checkedState.value = false
    }
}

@Composable
fun SearchScreen(navController: NavController, charactersViewModel: CharactersViewModel, fontFamily: FontFamily, checkedState: MutableState<Boolean>) {
    //  Colonna che contiene il layout della schermata di ricerca
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        //  Barra di ricerca
        SearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange = charactersViewModel::loadCharacterByNameList,
            checkedState
        )
        //  Lista di tutti gli eroi preferiti
        AllHeroesList(
            navController = navController,
            fontFamily = fontFamily,
            context = LocalContext.current,
            charactersViewModel = charactersViewModel
        )
    }
}

@Composable
fun AllHeroesList(navController: NavController, fontFamily: FontFamily, context: Context, charactersViewModel: CharactersViewModel) {

    //  Stato di scorrimento della lazyColumn
    val listState = rememberLazyListState()

    //  Caricamento dei primi 100 eroi
    val characterList =
        charactersViewModel.characterList

    /*  Il blocco si avvia o quando il composable viene avviato, o quando la dipendenza Unit cambia.
        Utilizzando Unit come dipendenza l'effetto viene avviato solo una volta all'avvio del composable */
    LaunchedEffect(Unit) {

        /*  snapshotFlow crea un flusso di snapshot basato sugli elementi visibili ottenuti da
            listState.layoutInfo.visibleItemsInfo. Il flusso emette un nuovo valore ogni volta
            che le informazioni sugli elementi visibili cambiano    */
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }

            //  .map trasforma ogni valore del flusso nel booleano isAtEnd, che indica se lo scorrimento è alla fine della lista
            .map { visibleItemsInfo ->

                //  Controlliamo se l'utlimo indice visibile è diverso da null, se il conteggio totale degli elementi totalCount è maggiore di 0
                //  e se l'ultimo indice visibile corrisponde all'ultimo elemento della lista (totalCount - 2)
                //  NOTA: ABBIAMO USATO -2 E NON -1 AFFINCHE' NON OTTENESSIMO L'ENTRATA NELL CICLO if PRIMA DEL CARICAMENTO DELLA LISTA, RISULTANDO QUINDI 0

                //  Ultimo indice visbile
                val lastVisibleIndex = visibleItemsInfo.lastOrNull()?.index

                //  Elementi totali
                val totalCount = listState.layoutInfo.totalItemsCount

                /*  Controlliamo se l'ultimo indice visibile è diverso da null e corrisponde all'ultimo
                    elemento della lista ed il conteggio degli elementi totali Ã¨ maggiore di 0  */
                val isAtEnd =
                    lastVisibleIndex != null && totalCount > 0 && lastVisibleIndex == totalCount - 80
                isAtEnd
            }

            //  Filtriamo solo i cambiamenti di stato, in modo che il flusso emetta solo valori diversi consecutivi
            .distinctUntilChanged()

            //  Raccogliamo i valori emessi dal flusso e quando raggiungiamo la fine della lista eseguiamo l'if
            .collect { endOfListReached ->
                if (endOfListReached) {
                    charactersViewModel.loadCharacterList()
                }
            }
    }

    //  LazyColumn in cui vengono caricati gli elementi della lista
    LazyColumn(state = listState) {
        items(characterList.size) { index ->
            //  Thumbnail relativa all'eroe
            HeroThumbnail(
                navController,
                fontFamily,
                characterList[index],
                context
            )
        }
    }
}

//  Thumbnail relativa ad un eroe
@Composable
fun HeroThumbnail(navController: NavController, fontFamily: FontFamily, selectedHero: Character, context: Context) {

    val notAv = stringResource(R.string.not_available).uppercase()

    //  Riga che contiene il composable dell'immagine e il nome dell'eroe
    Row(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //  Colonna che contiene l'immagine dell'eroe e il suo nome
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {

                    //  Estrazione dei dettagli dell'eroe selezionato
                    val name = selectedHero.name
                    var thumbnail = selectedHero.thumbnail?.path
                    var description = selectedHero.description
                    val id = selectedHero.id
                    val eventsAvailable = selectedHero.events?.available
                    val storiesAvailable = selectedHero.stories?.available
                    val comicsAvailable = selectedHero.comics?.available

                    if (description?.trim().isNullOrEmpty() || description == "#N/A") {
                        description = notAv
                    }

                    thumbnail = thumbnail?.replace("/", "_")

                    //  Creazione degli argomenti per la navigazione alla schermata dell'eroe
                    val args = listOf(
                        name, thumbnail, description, id, eventsAvailable, storiesAvailable, comicsAvailable
                    )

                    navController.navigate("heroScreen/${args.joinToString("/")}")
                }),
            verticalArrangement = Arrangement.Top
        ) {

            //  ImageView per mostrare l'immagine dell'eroe
            val imageView = remember { ImageView(context) }

            //  Caricamento dell'immagine dell'eroe utilizzando Picasso
            Picasso.get()
                .load((selectedHero.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                .placeholder(R.drawable.hero_placeholder)
                .resize(700, 550)
                .centerCrop()
                .into(imageView)


            //  Rappresentazione dell'immagine come Composable AndroidView
            AndroidView(
                factory = { imageView },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.785f)
            )

            //  Testo con il nome dell'eroe sopra l'immagine
            Text(
                text = selectedHero.name!!.uppercase(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.55f))
                    .height(50.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(vertical = 12.dp)
            )
        }
    }
}

//  "Stellina" per i preferiti
@Composable
fun FavouriteCheckbox(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {

    val colorState by animateColorAsState(
        targetValue = if (isChecked) Color.Yellow else Color.Gray,
        animationSpec = tween(
            durationMillis = 100, // Tempo per cambiare il colore (in millisecondi)
            easing = LinearEasing
        )
    )

    val scaleState by animateFloatAsState(
        targetValue = if (isChecked) 1.2f else 1f, // Cambia la scala all'istante del clic
        animationSpec = tween(
            durationMillis = 100, // Tempo per l'animazione di scala (in millisecondi)
            easing = LinearEasing
        )
    )

    val alphaState by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0.5f, // Cambia l'opacità  all'istante del clic
        animationSpec = tween(
            durationMillis = 50, // Tempo per l'animazione di opacità  (in millisecondi)
            easing = LinearEasing
        )
    )

    val scaleModifier = Modifier.scale(scaleState)

    //  Riga che contiene l'icona dei preferiti
    Row {
        //  Icona che cambia lo stato quando viene cliccata
        IconButton(
            onClick = { onCheckedChange(!isChecked) }
        ) {
            //  Icona a forma di stella, cambia colore in base allo stato
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = if (isChecked) "Favourite" else "Not Favourite",
                tint = colorState,
                modifier = Modifier
                    .size(32.dp)
                    .then(scaleModifier)
                    .alpha(alphaState)
            )
        }
    }
}

//  Spunta per i letti
@Composable
fun ReadComicCheckbox(isRead: Boolean, onReadChange: (Boolean) -> Unit) {
    val colorState by animateColorAsState(
        targetValue = if (isRead) Color.Green else Color.Gray,
        animationSpec = tween(
            durationMillis = 100, // Tempo per cambiare il colore (in millisecondi)
            easing = LinearEasing
        )
    )

    val scaleState by animateFloatAsState(
        targetValue = if (isRead) 1.2f else 1f, // Cambia la scala all'istante del clic
        animationSpec = tween(
            durationMillis = 100, // Tempo per l'animazione di scala (in millisecondi)
            easing = LinearEasing
        )
    )

    val alphaState by animateFloatAsState(
        targetValue = if (isRead) 1f else 0.5f, // Cambia l'opacità all'istante del clic
        animationSpec = tween(
            durationMillis = 50, // Tempo per l'animazione di opacità (in millisecondi)
            easing = LinearEasing
        )
    )

    val scaleModifier = Modifier.scale(scaleState)

    Row {
        IconButton(
            onClick = { onReadChange(!isRead) }
        ) {
            // Icona per i fumetti letti
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = if (isRead) "Read" else "Not Read",
                tint = colorState,
                modifier = Modifier
                    .size(30.dp)
                    .then(scaleModifier)
                    .alpha(alphaState)
            )
        }
    }
}