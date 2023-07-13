package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.data.repository.ComicsRepository

class LatestComicViewModelFactory (
        val comicsRepository: ComicsRepository,
    ): ViewModelProvider.Factory {
        //Il metodo create viene sovrascritto dalla factory
        //All'interno del metodo, viene creata un'istanza di CharactersViewModel utilizzando il charactersRepository passato nel costruttore della factory che viene castato a T (generic type)
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ComicsViewModel(comicsRepository, operationCode = 3) as T
        }
    }