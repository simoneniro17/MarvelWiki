package com.LCDP.marvelwiki

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


class ComicsPrintAllComics : ComponentActivity() {

    @Composable
    fun Prova(id: Int) {

        lateinit var comicsViewModel: ComicsViewModel
        val comicsRepository = ComicsRepository()
        val comicsViewModelProviderFactory = ComicsViewModelFactory(comicsRepository, id)
        comicsViewModel = ViewModelProvider(
            this,
            comicsViewModelProviderFactory
        ).get(ComicsViewModel::class.java)


        // Richiama il composable CharactersScreen
        ComicsScreen(comicsViewModel)

    }

    @Composable
    fun ComicsScreen(comicsViewModel: ComicsViewModel) {

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
                val comicList = comicResponse?.comicData?.results

                comicList?.forEach { comic ->
                    println(comic.title)
                }

                if (comicList != null) {
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
}