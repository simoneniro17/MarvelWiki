package com.LCDP.marvelwiki.data.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.database.appDatabase
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.data.database.model.FavouriteComic
import com.LCDP.marvelwiki.data.database.repository.FavouriteCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteCharacterViewModel(application: Application): AndroidViewModel(application) {

    // Oggetto 'LiveData' contenente la lista dei preferiti
    val readAllData: LiveData<List<FavouriteCharacter>>

    // Istanza del FavCharRepository
    private val repository: FavouriteCharacterRepository

    // Viene inizializzato il FavCharRep utilizzando il DAO e viene impostato l'oggetto 'readAllData'
    init {
        val favouriteCharacterDAO = appDatabase.getDatabase(application).favouriteCharacterDAO()
        repository = FavouriteCharacterRepository(favouriteCharacterDAO)
        readAllData = repository.readAllData
    }

    fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        // L'operazione di inserimento è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavouriteCharacter(favouriteCharacter)
        }
    }

    fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        // L'operazione di cancellazione è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavouriteCharacter(favouriteCharacter)
        }
    }
}