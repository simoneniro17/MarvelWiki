package com.LCDP.marvelwiki.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.repository.CharactersRepository

//  Factory per creare istanze di CharactersViewModel
class CharactersViewModelFactory(
    private val charactersRepository: CharactersRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /*  Creazione istanza di CharactersViewModel utilizzando il repository dei personaggi
            e l'application forniti. Viene poi fatto il cast a T (generic type) */
        return CharactersViewModel(charactersRepository, application) as T
    }
}