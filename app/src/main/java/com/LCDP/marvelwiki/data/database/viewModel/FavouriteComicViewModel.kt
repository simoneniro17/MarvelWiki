package com.LCDP.marvelwiki.data.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.database.appDatabase
import com.LCDP.marvelwiki.data.database.model.FavouriteComic
import com.LCDP.marvelwiki.data.database.repository.FavouriteComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteComicViewModel(application: Application): AndroidViewModel(application){

    //Oggetto 'LiveData' contenente la lista dei preferiti
    val readAllData: LiveData<List<FavouriteComic>>

    //Istanza del FavoureiteComicRepository
    private val repository: FavouriteComicRepository

    //Istanza del FavoureiteComicRepository utilizzando il DAO e viene impostato l'oggetto 'readAllData'
    init {
        val favouriteComicDAO = appDatabase.getDatabase(application).favouriteComicDAO()
        repository = FavouriteComicRepository(favouriteComicDAO)
        readAllData = repository.readAllData
    }

    fun addFavouriteComic(favouriteComic: FavouriteComic){
        //L'operazione di inserimento è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavouriteComic(favouriteComic)
        }
    }

}