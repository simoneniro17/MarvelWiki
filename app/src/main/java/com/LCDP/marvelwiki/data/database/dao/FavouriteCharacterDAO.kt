package com.LCDP.marvelwiki.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter

// Questa interfaccia definisce le operazioni di accesso e manipolazione dei dati per l'entità 'FavouriteCharacter'
@Dao
interface FavouriteCharacterDAO {

    // Metodo per aggiungere un personaggio preferito al database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter)

    /*
        Metodo per leggere tutti i dati della tabella "FavouriteCharacter"
        Infatti, il metodo readAllData() restituisce un oggetto 'LiveData' (mantiene aggiornati i dati
        automaticamente quando vengono apportate modifiche nel DB) contenente una lista di tutti i
        personaggi preferiti presenti nel database.
     */
    @Query("SELECT * FROM FavouriteCharacter")
    fun readAllData(): LiveData<List<FavouriteCharacter>>

}