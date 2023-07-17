package com.LCDP.marvelwiki.database.printer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.ComicsByIsbnViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsByNameViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.usefulStuff.Resource

class PrintComicByName : ComponentActivity() {

    // Creazione istanza repository, che verrà passata a 'RetrieveAllChar'
    private val comicsRepository = ComicsRepository()

    private val name = ""   //TODO trovare un modo per passarlo dinamicamente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrieveComicByName(name, comicsRepository)
        }
    }
}

@Composable
fun RetrieveComicByName(name:String, comicsRepository: ComicsRepository) {

    // Creazione istanza del ViewModel dalla factory, che verrà passata a 'ComicsScreen'
    val comicsViewModel: ComicsViewModel = viewModel(
        factory = ComicsByNameViewModelFactory(comicsRepository, name)
    )
    ComicsScreenByName(comicsViewModel)
}

@Composable
fun ComicsScreenByName(comicsViewModel: ComicsViewModel) {

    // Osserviamo le modifiche della proprietà 'comics' del 'comicsViewModel'
    val comics: Resource<ComicResponse> by comicsViewModel.comics.observeAsState(
        Resource.Loading()
    )

    when (comics) {
        is Resource.Loading -> {
            // Mostra il caricamento dei dati
        }

        is Resource.Success -> {
            // Mostra i dati dei fumetti
            val comicResponse = comics.data

            // Istanziamo la lista dei fumetti
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