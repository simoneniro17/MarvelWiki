package com.LCDP.marvelwiki.database

import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseAccess(private val myDatabase: appDatabase) {

    suspend fun getAllFavouriteCharacters():List<String>{
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().getAllFavoriteCharacterId()
        }
    }

    suspend fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().deleteFavouriteCharacter(favouriteCharacter)
        }
    }

    suspend fun insertFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().insertFavoriteCharacter(favouriteCharacter)
        }
    }
}