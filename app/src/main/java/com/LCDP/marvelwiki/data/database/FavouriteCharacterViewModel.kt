package com.LCDP.marvelwiki.data.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteCharacterViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<FavouriteCharacter>>
    private val repository: FavouriteCharacterRepository
    init {
        val favouriteCharacterDAO = appDatabase.getDatabase(application).favouriteCharacterDAO()
        repository = FavouriteCharacterRepository(favouriteCharacterDAO)
        readAllData = repository.readAllData
    }

    fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavouriteCharacter(favouriteCharacter)
        }
    }
}