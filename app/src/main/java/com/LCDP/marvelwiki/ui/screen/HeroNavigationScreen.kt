package com.LCDP.marvelwiki.ui.screen

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.derivedStateOf
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.LCDP.marvelwiki.R
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.HeroModel
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.usefulStuff.Debouncer
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun NavigationScreen(navController: NavController,context: Context) {

    //Setup del font
    val currentFont = FontFamily(Font(R.font.ethnocentric_font, FontWeight.Thin))

    val charactersRepository = CharactersRepository()               //creo la repository
    val charactersViewModel: CharactersViewModel = viewModel(       //creo il viewModel dalla sua factory
        factory = CharactersViewModelFactory(charactersRepository)
    )

    charactersViewModel.loadCharacterList()                     //chiamo il metodo del viewModel che permette di caricare
    //prendo dal viewModel la characterList caricata che, alla prima apertura contiene solo i primi 100 eroi

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

        //Costruzione dei dettagli (ho associato ad ogni parte della schermata un metodo (evidenziato in rosso) che la crea per motivi di ordine)
        //DA NOTARE che ai NavigationButtons deve essere passato il navController, per poterlo utilizzare per switchare schermata quando clicchi il bottone)
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
        ) {
            NavigationScreenUpperBar(
                navController,
                currentFont
            )             //Creazione del layout esterno alla lazy list (la barra fissa in alto)
            //Separator(fontFamily = currentFont)
            SearchScreen(navController = navController, charactersViewModel = charactersViewModel, fontFamily = currentFont)
            AllHeroesList(navController, currentFont,context, charactersViewModel)
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
                fontSize = 30.sp,
                color = Color.White,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
            )

        }
    }

@Composable
fun SearchBar(
    fontFamily: FontFamily,
    onSearchQueryChange: (String) -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }
    val debouncer = remember { Debouncer(300)}
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
                debouncer.debounce {onSearchQueryChange(it)}
            },
            label = {
                Text(
                    text = "Search a character".uppercase(),
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
fun SearchScreen(navController: NavController,
                 charactersViewModel: CharactersViewModel,
                 fontFamily: FontFamily) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            fontFamily = fontFamily,
            onSearchQueryChange =  charactersViewModel::loadCharacterByNameList
        )
        AllHeroesList(
            navController = navController,
            fontFamily = fontFamily,
            context = LocalContext.current,
            charactersViewModel = charactersViewModel
        )
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
                fontSize = 12.sp,
                fontFamily = fontFamily,
                color = Color.White
            )
        }
    }




@Composable
fun AllHeroesList(
    navController: NavController,
    fontFamily: FontFamily,
    context: Context,
    charactersViewModel: CharactersViewModel

) {
    // listState utilizzata per avere un controllo preciso sullo stato di scorrimento della lazyColumn
    val listState = rememberLazyListState()
    val characterList = charactersViewModel.characterList       //prendo dal viewModel la characterList caricata che, alla prima apertura contiene solo i primi 100 eroi

    //in blocco viene avviato quando il composable viene avviato o quando la dipendenza Unit cambia
    //in questo caso, stiamo utilizzando Unit come dipendenza, quindi l'effetto viene avviato solo una volta all'avvio del compose
    LaunchedEffect(Unit) {

        //snapshotFlow { listState.layoutInfo.visibleItemsInfo } viene utilizzato per creare un flusso di snapshot basato sulle informazioni
        //sugli elementi visibili ottenute da listState.layoutInfo.visibleItemsInfo.
        //Questo flusso emette un nuovo valore ogni volta che le informazioni sugli elementi visibili cambiano
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            //.map lo utilizziamo per trasformare ogni valore emesso dal flusso nel valore booleano isAtEnd che indica che lo scorrimento è alla fine della lista
            //controlliamo se l'utlimo indice visibile è diverso da null, se il conteggio totale degli elementi totalCount è maggiore di 0
            //e se l'ultimo indice visibile corrisponde all'ultimo elemento della lista (totalCount - 2)
            //NOTA: ABBIAMO USATO -2 E NON -1 AFFINCHE' NON OTTENESSIMO L'ENTRATA NELL CICLO if PRIMA DEL CARICAMENTO DELLA LISTA, RISULTANDO QUINDI 0
            .map { visibleItemsInfo ->
                val lastVisibleIndex = visibleItemsInfo.lastOrNull()?.index
                val totalCount = listState.layoutInfo.totalItemsCount
                val isAtEnd = lastVisibleIndex != null && totalCount > 0 && lastVisibleIndex == totalCount - 80
                isAtEnd
            }
            //utilizziamo il seguente operatore per filtrare solo i cambiamenti di stato nel flusso. In questo modo il flusso emetterà
            //solo valori diversi consecutivi
            .distinctUntilChanged()
            //raccoglie i valori emessi dal flusso, quando endOfListReached diventa true eseguiamo la if
            .collect { endOfListReached ->
                if (endOfListReached) {
                    charactersViewModel.loadCharacterList()                         //richiama dal viewModel il metodo che richiama la api per caricare nuovi personaggi
                }
            }
    }

    LazyColumn(state = listState) {                                  //ora carichiamo gli elementi della lista
        items(characterList.size) { index ->
            HeroThumbnail(
                navController,
                fontFamily,
                characterList[index],
                context
            )
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


                        val name = selectedHero.name
                        var thumbnail = selectedHero.thumbnail?.path

                        thumbnail = thumbnail?.replace("/","_")
                        var description = selectedHero.description

                        if (description.isNullOrEmpty()){
                            description = "DESCRIPTION NOT FOUND"
                        }

                        val args = listOf(
                            name,
                            //"thumbnail_sample",//selectedHero.thumbnail,
                            thumbnail,
                            description
                        )
                        navController.navigate("heroScreen/${args.joinToString("/")}")

                    }
                    ),
                verticalArrangement = Arrangement.Top
            ) {
                val imageView = remember { ImageView(context) }

                Picasso.get()
                    .load((selectedHero.thumbnail?.path?.replace("http://", "https://")) + ".jpg")
                    .placeholder(R.drawable.sfondo_muro)                                                                            //attesa del carimento, da cmabiare
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