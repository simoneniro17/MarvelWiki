package com.LCDP.marvelwiki.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.database.model.FavouriteCharacter

// Interfaccia che definisce le operazioni di accesso e manipolazione dei dati per l'entità 'favourite_character'
@Dao
interface FavouriteCharacterDAO {

    // Metodo per aggiungere (l'ID di) un personaggio ai preferiti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteCharacter(favouriteCharacter: FavouriteCharacter)

    // Metodo per rimuovere un personaggio preferito dal database
    @Delete
    suspend fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter)

    // Metodo per ottenere la lista dei personaggi (gli ID) preferiti
    @Query("SELECT * FROM favourite_character")
    fun getAllFavoriteCharacterId(): List<String>
}