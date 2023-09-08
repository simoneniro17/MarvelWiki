package com.lcdp.marvelwiki.database

import com.lcdp.marvelwiki.database.model.FavouriteCharacter
import com.lcdp.marvelwiki.database.model.FavouriteComic
import com.lcdp.marvelwiki.database.model.ReadComic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseAccess(private val myDatabase: AppDatabase) {

    //  Per ottenere l'elenco degli ID di tutti i personaggi preferiti
    suspend fun getAllFavouriteCharacters(): List<String> {
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().getAllFavoriteCharacterId()
        }
    }

    //  Per inserire l'ID di un personaggio tra i preferiti
    suspend fun insertFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().insertFavoriteCharacter(favouriteCharacter)
        }
    }

    //  Per eliminare l'ID di un personaggio dai preferiti
    suspend fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteCharacterDAO().deleteFavouriteCharacter(favouriteCharacter)
        }
    }

    //  Per verificare se l'ID di un personaggio è tra i preferiti
    suspend fun isCharacterFavourite(selectedId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val idList = myDatabase.favouriteCharacterDAO().getAllFavoriteCharacterId()
            idList.contains(selectedId)
        }
    }

    //  Per ottenere l'elenco degli ID di tutti i fumetti preferiti
    suspend fun getAllFavouriteComics(): List<String> {
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().getAllFavoriteComicId()
        }
    }

    //  Per inserire l'ID di un fumetto tra i preferiti
    suspend fun insertFavouriteComic(favouriteComic: FavouriteComic) {
        withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().insertFavoriteComic(favouriteComic)
        }
    }

    //  Per eliminare l'ID di un fumetto dai preferiti
    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic) {
        return withContext(Dispatchers.IO) {
            myDatabase.favouriteComicDAO().deleteFavouriteComic(favouriteComic)
        }
    }

    //  Per verificare se l'ID di un fumetto è tra i preferiti
    suspend fun isComicFavourite(selectedId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val idList = myDatabase.favouriteComicDAO().getAllFavoriteComicId()
            idList.contains(selectedId)
        }
    }

    //  Per ottenere l'elenco degli ID di tutti i fumetti letti
    suspend fun getAllReadComics(): List<String> {
        return withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().getAllReadComicId()
        }
    }

    //  Per inserire l'ID di un fumetto tra quelli letti
    suspend fun insertReadComic(readComic: ReadComic) {
        withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().insertReadComic(readComic)
        }
    }

    //  Per eliminare l'ID di un fumetto da quelli letti
    suspend fun deleteReadComic(readComic: ReadComic) {
        return withContext(Dispatchers.IO) {
            myDatabase.readComicDAO().deleteReadComic(readComic)
        }
    }

    //  Per verificare se l'ID di un fumetto è tra quelli letti
    suspend fun isComicRead(selectedId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val idList = myDatabase.readComicDAO().getAllReadComicId()
            idList.contains(selectedId)
        }
    }
}