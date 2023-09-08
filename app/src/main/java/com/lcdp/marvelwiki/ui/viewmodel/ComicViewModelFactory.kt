package com.lcdp.marvelwiki.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcdp.marvelwiki.data.repository.ComicsRepository

//  Factory per creare istanze di ComicViewModel
class ComicViewModelFactory(
    private val comicsRepository: ComicsRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /*  Creazione istanza di ComicViewModel utilizzando il repository dei fumetti
            e l'application forniti. Viene poi fatto il cast a T (generic type) */
        return ComicsViewModel(comicsRepository, application) as T
    }
}