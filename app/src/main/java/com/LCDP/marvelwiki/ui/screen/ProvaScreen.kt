package com.LCDP.marvelwiki.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.usefulStuff.Resource

//Questa è una prova dello screen. È uno scheletro e bisogna vedere se effettivamente è una giusta implementazione
@Composable
fun CharactersScreen(charactersViewModel: CharactersViewModel) {
    val characters: Resource<CharacterResponse> by charactersViewModel.characters.observeAsState(Resource.Loading())
    //charactersViewModel è un parametro passato al composable CharactersScreen. Attraverso charactersViewModel.characters.observeAsState(),
    //il composable osserva i dati del ViewModel e reagisce ai cambiamenti nello stato dei dati.

    when (characters) {
        is Resource.Loading -> {
            // Mostra il caricamento dei dati
        }
        is Resource.Success -> {
            // Mostra i dati dei personaggi
            val characterResponse = characters.data
            // ...
        }
        is Resource.Error -> {
            // Mostra un messaggio di errore
            val errorMessage = characters.message
            // ...
        }
    }

    // ...
}