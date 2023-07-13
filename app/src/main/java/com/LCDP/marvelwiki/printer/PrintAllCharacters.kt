package com.LCDP.marvelwiki.printer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


class CharacterPrintAllCharacters : ComponentActivity() {

    // Creazione istanza repository, che verrà passata a 'RetrieveAllChar'
    private val charactersRepository = CharactersRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrieveAllChar(charactersRepository)
        }
    }
}

@Composable
fun RetrieveAllChar(charactersRepository: CharactersRepository) {

    // Creazione istanza del ViewModel dalla factory, che verrà passata a 'CharactersScreen'
    val charactersViewModel: CharactersViewModel = viewModel(
        factory = CharactersViewModelFactory(charactersRepository)
    )
    CharactersScreen(charactersViewModel)
}

@Composable
fun CharactersScreen(charactersViewModel: CharactersViewModel) {

    // Osserviamo le modifiche della proprietà 'characters' del 'charactersViewModel'
    val characters: Resource<CharacterResponse> by charactersViewModel.characters.observeAsState(
        Resource.Loading()
    )

    when (characters) {
        is Resource.Loading -> {
            // Mostra il caricamento dei dati
        }

        is Resource.Success -> {
            // Mostra i dati dei personaggi
            val characterResponse = characters.data

            // Istanziamo la lista dei personaggi
            val characterList =
                characterResponse?.characterData?.results

            // Tramite un loop, scorriamo la lista e stampiamo i nomi
            characterList?.forEach { character ->
                println(character.name)
            }

            // Se la lista non è vuota, vengono richiesti ulteriori personaggi
            if (characterList != null) {
                if (characterList.isNotEmpty()) {
                    charactersViewModel.loadMoreCharacters()
                }
            }
        }

        is Resource.Error -> {
            // Mostra un messaggio di errore
            val errorMessage = characters.message
            // ...
        }
    }
    // ...
}

