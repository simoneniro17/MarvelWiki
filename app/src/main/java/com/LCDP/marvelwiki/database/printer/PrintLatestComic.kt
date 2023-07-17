package com.LCDP.marvelwiki.database.printer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.ui.viewmodel.LatestComicViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource


@Composable
fun retrieveLatestComicPath(): String? {
    val comicsRepository = ComicsRepository()
    val comicsViewModel: ComicsViewModel = viewModel(
        factory = LatestComicViewModelFactory(comicsRepository)
    )

    // Osserviamo le modifiche della proprietà 'comics' del 'comicsViewModel'
    val comics: Resource<ComicResponse> by comicsViewModel.comics.observeAsState(
        Resource.Loading()
    )

    when (comics) {
        is Resource.Loading -> run {
            return "https://mir-s3-cdn-cf.behance.net/project_modules/disp/da734b28921021.55d95297d71f4.gif"
        }

        is Resource.Success -> {
            // Mostra i dati dei fumetti
            val comicResponse = comics.data

            // Istanziamo la lista dei fumetti
            val comicList = comicResponse?.comicData?.results
            return modifyPath(comicList?.get(0)?.thumbnail?.path)
        }

        is Resource.Error -> {

        }
    }
    return null
}

@Composable
fun retrieveLatestComicId(): String? {
    val comicsRepository = ComicsRepository()
    val comicsViewModel: ComicsViewModel = viewModel(
        factory = LatestComicViewModelFactory(comicsRepository)
    )

    // Osserviamo le modifiche della proprietà 'comics' del 'comicsViewModel'
    val comics: Resource<ComicResponse> by comicsViewModel.comics.observeAsState(
        Resource.Loading()
    )

    when (comics) {
        is Resource.Loading -> run {
            return "https://mir-s3-cdn-cf.behance.net/project_modules/disp/da734b28921021.55d95297d71f4.gif"
        }

        is Resource.Success -> {
            // Mostra i dati dei fumetti
            val comicResponse = comics.data

            // Istanziamo la lista dei fumetti
            val comicList = comicResponse?.comicData?.results
            return modifyPath(comicList?.get(0)?.comicId.toString())
        }

        is Resource.Error -> {

        }
    }
    return null
}

fun modifyPath(originalUrl: String?): String {
    return originalUrl?.replace("http://", "https://") + ".jpg"
}

