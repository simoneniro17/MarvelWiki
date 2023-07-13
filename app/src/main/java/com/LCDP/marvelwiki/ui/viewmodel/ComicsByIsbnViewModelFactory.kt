package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.repository.ComicsRepository


//La classe ComicsByIsbnViewModelFactory implementa l'interfaccia ViewModelProvider.Factory
//riceve un parametro comicsRepository di tipo ComicsRepository, che sarà utilizzato per creare il ViewModel.
class ComicsByIsbnViewModelFactory (
    private val comicsRepository: ComicsRepository,
    var isbn: String
): ViewModelProvider.Factory {

    //Il metodo create viene sovrascritto dalla factory
    //All'interno del metodo, viene creata un'istanza di ComicsViewModel utilizzando il comicsRepository passato nel costruttore della factory che viene castato a T (generic type)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComicsViewModel(comicsRepository,  isbn = isbn) as T
    }
}