package com.LCDP.marvelwiki.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.repository.FavouriteCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*class FavouriteCharacterViewModel(application: Application): AndroidViewModel(application) {

    // Oggetto contenente la lista (degli ID) dei personaggi preferiti
    val allFavouriteCharacterId: List<String>

    // Istanza del FavouriteCharacterRepository
    private val repository: FavouriteCharacterRepository

    // Viene inizializzato il FavouriteCharacterRepository utilizzando il DAO e viene impostato l'oggetto 'allFavouriteCharacterId'
    init {
        val favouriteCharacterDAO = appDatabase.getDatabase(application).favouriteCharacterDAO()
        repository = FavouriteCharacterRepository(favouriteCharacterDAO)
        allFavouriteCharacterId = repository.allFavouriteCharacterId
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

 */

class FavouriteCharacterViewModel(
    private val favouriteCharacterRepository: FavouriteCharacterRepository
) : ViewModel() {

    val allFavouriteCharacterId: List<String> = favouriteCharacterRepository.allFavouriteCharacterId

    fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteCharacterRepository.addFavouriteCharacter(favouriteCharacter)
        }
    }

    fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteCharacterRepository.deleteFavouriteCharacter(favouriteCharacter)
        }
    }
}