package com.LCDP.marvelwiki.printer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


@Composable
fun retrieveCharacterList(): List<Character>? {

    val charactersRepository = CharactersRepository()
    val charactersViewModel: CharactersViewModel = viewModel(
        factory = CharactersViewModelFactory(charactersRepository)
    )

    // Osserviamo le modifiche della proprietà 'characters' del 'charactersViewModel'
    val characters: Resource<CharacterResponse> by charactersViewModel.characters.observeAsState(
        Resource.Loading()
    )

    when (characters) {
        is Resource.Loading -> {
        }

        is Resource.Success -> {
            // Mostra i dati dei personaggi
            val characterResponse = characters.data

            // Istanziamo la lista dei personaggi
            val characterList =
                characterResponse?.characterData?.results

            // Se la lista non è vuota, vengono richiesti ulteriori personaggi
            /*if (characterList != null) {
                if (characterList.isNotEmpty()) {
                    charactersViewModel.loadMoreCharacters()
                }
            }

             */

            return characterList
        }
        is Resource.Error -> {
            // Mostra un messaggio di errore
            val errorMessage = characters.message
            // ...
        }
    }
return null
}

