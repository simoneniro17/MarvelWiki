package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.ui.screen.CharactersScreen
import com.LCDP.marvelwiki.ui.screen.Navigation
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource

class MainActivity : ComponentActivity() {

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Costruzione del Navigator che permette di switchare tra le schermate
            //Navigation()
            //Per farlo ritornare come prima decommenta la riga sopra e commenta il resto sotto


            lateinit var charactersViewModel: CharactersViewModel
            val characterRepository = CharactersRepository()
            val characterViewModelProviderFactory = CharactersViewModelFactory(characterRepository)
            charactersViewModel = ViewModelProvider(
                this,
                characterViewModelProviderFactory
            ).get(CharactersViewModel::class.java)


            // Richiama il composable CharactersScreen
            CharactersScreen(charactersViewModel)

        }
    }


    @Composable
    fun CharactersScreen(charactersViewModel: CharactersViewModel) {

        val characters: Resource<CharacterResponse> by charactersViewModel.characters.observeAsState(
            Resource.Loading()
        )
        val loadingState = remember { mutableStateOf(true) }

        when (characters) {
            is Resource.Loading -> {
                // Mostra il caricamento dei dati
            }
            is Resource.Success -> {
                // Mostra i dati dei personaggi
                val characterResponse = characters.data
                val characterList = characterResponse?.characterData?.results

                characterList?.forEach { character ->
                    println(character.name)
                }

                LaunchedEffect(loadingState.value) {
                    if (loadingState.value) {
                        charactersViewModel.loadMoreCharacters()
                        loadingState.value = false
                    }
                }

                if (characterList.isNullOrEmpty()) {
                    loadingState.value = true
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
}



