package com.LCDP.marvelwiki.data.database

import androidx.lifecycle.LiveData

class FavouriteCharacterRepository(private val favouriteCharacterDAO: FavouriteCharacterDAO) {

    val readAllData: LiveData<List<FavouriteCharacter>> = favouriteCharacterDAO.readAllData()

    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        favouriteCharacterDAO.addFavouriteCharacter(favouriteCharacter)
    }
}