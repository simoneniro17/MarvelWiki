package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


class ComicsPrintAllComics : ComponentActivity() {
    private val comicsRepository = ComicsRepository()               //creo la repository
    private val id:Int = 123                                        //TODO trovare un modo per passarlo dinamicamente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrieveAllComic(id, comicsRepository)
        }
    }
}

    @Composable
    fun RetrieveAllComic(id: Int, comicsRepository: ComicsRepository) {
        val comicsViewModel: ComicsViewModel = viewModel(                   //creo il viewmodel dalla factory e lo passo a comicScreen
            factory = ComicsViewModelFactory(comicsRepository,id)
        )
        ComicsScreen(comicsViewModel)
    }

    @Composable
    fun ComicsScreen(comicsViewModel: ComicsViewModel) {
    //viene osservata la risorsa comics del charactersViewModel
        val comics: Resource<ComicResponse> by comicsViewModel.comics.observeAsState(
            Resource.Loading()
        )

        when (comics) {
            is Resource.Loading -> {
                // Mostra il caricamento dei dati
            }

            is Resource.Success -> {
                // Mostra i dati dei personaggi
                val comicResponse = comics.data
                val comicList = comicResponse?.comicData?.results           //path per ottenere la lista di comics

                comicList?.forEach { comic ->                               //loop che scorre la lista e stampa i titoli
                    println(comic.title)
                }

                if (comicList != null) {                                    //se la lista non è vuota rimanda una richiesta per ricevere ulteriori comic
                    if (comicList.isNotEmpty()) {
                        comicsViewModel.loadMoreComics()
                    }
                }
            }

            is Resource.Error -> {
                // Mostra un messaggio di errore
                val errorMessage = comics.message
                // ...
            }
        }
        // ...
    }
