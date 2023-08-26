package com.LCDP.marvelwiki.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.database.model.FavouriteComic

// Interfaccia che definisce le operazioni di accesso e manipolazione dei dati per l'entità 'favourite_comic'
@Dao
interface FavouriteComicDAO {

    // Metodo per aggiungere (l'ID di) un comic ai preferiti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteComic(favouriteComic: FavouriteComic)

    // Metodo per rimuovere un fumetto dai preferiti
    @Delete
    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic)

    // Metodo per ottenere la lista (degli ID) dei comic preferiti
    @Query("SELECT * FROM favourite_comic")
    fun getAllFavoriteComicId(): List<String>

    @Query("SELECT EXISTS (SELECT 1 FROM favourite_comic WHERE comicId = :comicId LIMIT 1)")
    suspend fun isComicFavourite(comicId: String): Boolean
}
