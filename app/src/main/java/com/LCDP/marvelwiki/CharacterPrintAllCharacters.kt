package com.LCDP.marvelwiki


import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


class CharacterPrintAllCharacters : ComponentActivity(){

    @Composable
    fun Prova() {
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


    @Composable
    fun CharactersScreen(charactersViewModel: CharactersViewModel) {

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
                val characterList = characterResponse?.characterData?.results

                characterList?.forEach { character ->
                    println(character.name)
                }

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
}
