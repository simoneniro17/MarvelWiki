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
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.ui.viewmodel.LatestComicsByCharIdViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource

class ComicsPrintAllComics : ComponentActivity() {

    // Creazione istanza repository, che verrà passata a 'RetrieveAllChar'
    private val comicsRepository = ComicsRepository()

    private val id: Int = 123   //TODO trovare un modo per passarlo dinamicamente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrieveLatestComicsByCharId(id, comicsRepository)
        }
    }
}

@Composable
fun RetrieveLatestComicsByCharId(id: Int, comicsRepository: ComicsRepository) {

    // Creazione istanza del ViewModel dalla factory, che verrà passata a 'ComicsScreen'
    val comicsViewModel: ComicsViewModel = viewModel(
            factory = LatestComicsByCharIdViewModelFactory(comicsRepository, id)
        )
    ComicsScreenByCharId(comicsViewModel)
}

@Composable
fun ComicsScreenByCharId(comicsViewModel: ComicsViewModel) {

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
            val comicList =
                comicResponse?.comicData?.results

            // Tramite un loop, scorriamo la lista e stampiamo i titoli
            comicList?.forEach { comic ->
                println(comic.title)
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
