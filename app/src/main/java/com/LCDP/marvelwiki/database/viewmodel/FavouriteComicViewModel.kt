package com.LCDP.marvelwiki.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.repository.FavouriteComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteComicViewModel(application: Application): AndroidViewModel(application){

    // Oggetto contenente la lista (degli ID) dei fumetti preferiti
    val allFavouriteComicId: List<String>

    // Istanza del FavouriteComicRepository
    private val repository: FavouriteComicRepository

    // Viene inizializzato il FavouriteComicRepository utilizzando il DAO e viene impostato l'oggetto 'allFavouriteComicId'
    init {
        val favouriteComicDAO = appDatabase.getDatabase(application).favouriteComicDAO()
        repository = FavouriteComicRepository(favouriteComicDAO)
        allFavouriteComicId = repository.allFavouriteComicId
    }

    fun addFavouriteComic(favouriteComic: FavouriteComic){
        // L'operazione di inserimento è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavouriteComic(favouriteComic)
        }
    }

    fun deleteFavouriteComic(favouriteComic: FavouriteComic){
        // L'operazione di cancellazione è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavouriteComic(favouriteComic)
        }
    }
}