package com.LCDP.marvelwiki


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
    private val charactersRepository = CharactersRepository()               //creo la repository e la passo come parametro in retrieve all character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrieveAllChar(charactersRepository)
        }
    }
}

@Composable
fun RetrieveAllChar(charactersRepository: CharactersRepository) {
    val charactersViewModel: CharactersViewModel = viewModel(               //creo il viewmodel dalla factory e lo passo a character screen
        factory = CharactersViewModelFactory(charactersRepository)
    )
    CharactersScreen(charactersViewModel)
}

    @Composable
    fun CharactersScreen(charactersViewModel: CharactersViewModel) {
        //viene osservata la risorsa characters del charactersViewModel
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
                val characterList = characterResponse?.characterData?.results       //path per ottenere la lista di personaggi

                characterList?.forEach { character ->           //loop che scorre la lista e stampa i nomi
                    println(character.name)
                }

                if (characterList != null) {                    //se la lista non è vuota rimanda una richiesta per ricevere ulteriori personaggi
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

