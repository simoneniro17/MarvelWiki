package com.LCDP.marvelwiki.database

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.model.ReadComic
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

    suspend fun getAllFavouriteComics():List<String>{
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().getAllFavoriteComicId()
        }
    }

    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic){
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().deleteFavouriteComic(favouriteComic)
        }
    }

    suspend fun insertFavouriteComic(favouriteComic: FavouriteComic) {
        withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().insertFavoriteComic(favouriteComic)
        }
    }

    /*
    suspend fun isComicFavourite(favouriteComic: FavouriteComic): Boolean {
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().isComicFavourite(favouriteComic.comicId)
        }
    }

     */

    suspend fun getAllReadComics():List<String>{
        return withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().getAllReadComicId()
        }
    }

    suspend fun deleteReadComic(readComic: ReadComic){
        return withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().deleteReadComic(readComic)
        }
    }

    suspend fun insertReadComic(readComic: ReadComic) {
        withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().insertReadComic(readComic)
        }
    }

    /*
    suspend fun isComicRead(readComic: ReadComic): Boolean {
        return withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().isComicRead(readComic.comicId)
        }
    }

     */
}