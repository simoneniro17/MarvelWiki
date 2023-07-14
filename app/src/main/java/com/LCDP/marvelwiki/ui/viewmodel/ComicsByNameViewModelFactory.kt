package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.repository.ComicsRepository

class ComicsByNameViewModelFactory (
        private val comicsRepository: ComicsRepository,
        var name: String
    ): ViewModelProvider.Factory {

        //Il metodo create viene sovrascritto dalla factory
        //All'interno del metodo, viene creata un'istanza di ComicsViewModel utilizzando il comicsRepository passato nel costruttore della factory che viene castato a T (generic type)
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ComicsViewModel(comicsRepository,  name = name, operationCode = 4) as T
        }
    }
