package com.LCDP.marvelwiki.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.repository.FavouriteComicRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/* class FavouriteComicViewModel(application: Application): AndroidViewModel(application){

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
} */

class FavouriteComicViewModel( private val databaseAccess: DatabaseAccess) : ViewModel() {

    private var favouriteComicList = emptyList<String>()
    private var isFavourite : Boolean = false

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchData():List<String>{
        GlobalScope.launch {
            favouriteComicList = databaseAccess.getAllFavouriteComics()
        }
        return favouriteComicList
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insertData(favouriteComic: FavouriteComic) {
        GlobalScope.launch {
            databaseAccess.insertFavouriteComic(favouriteComic)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteData(favouriteComic: FavouriteComic) {
        GlobalScope.launch {
            databaseAccess.deleteFavouriteComic(favouriteComic)
        }
    }

    /*
    @OptIn(DelicateCoroutinesApi::class)
    fun isComicFavourite(favouriteComic: FavouriteComic) : Boolean {
        GlobalScope.launch {
            isFavourite = databaseAccess.isComicFavourite(favouriteComic)
        }
        return isFavourite
    }

     */
}