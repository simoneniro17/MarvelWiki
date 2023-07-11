package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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


            // Crea l'istanza del repository
            val charactersRepository = CharactersRepository()
            // Crea l'istanza del ViewModel e del ViewModelFactory
            val charactersViewModelFactory = CharactersViewModelFactory(charactersRepository)
            val charactersViewModel = charactersViewModelFactory.create(CharactersViewModel::class.java)

            // Richiama il composable CharactersScreen
            CharactersScreen(charactersViewModel)               } } }



/*
                // Stampa i campi dei personaggi

            when (val characters = charactersViewModel.characters.value) {
                    is Resource.Success -> {
                        characters.data?.characterData?.results?.forEach {  

                        character ->
                            println("Nome: ${character.name}")
                            println("Descrizione: ${character.description}")
                            println("Thumbnail: ${character.thumbnail}")
                            println()
                        }
                    }
                    is Resource.Error -> {
                        println("Errore: ${characters.message}")
                    }
                    is Resource.Loading -> {
                        println("Caricamento...")
                    }

                    else -> {}
                }
            }
        }
    }
*/