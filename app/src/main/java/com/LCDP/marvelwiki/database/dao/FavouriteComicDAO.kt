package com.LCDP.marvelwiki.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic

// Questa interfaccia definisce le operazioni di accesso e manipolazione dei dati per l'entità 'FavouriteComic'

@Dao
interface FavouriteComicDAO {

    //  Metodo per aggiungere un fumetto ai preferiti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteComic(favouriteComic: FavouriteComic)

    //  Metodo per rimuovere un fumetto dai preferito
    @Delete
    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic)

    /*
        Metodo per leggere tutti i dati della tabella "FavouriteComic"
        Infatti, il metodo readAllData() restituisce un oggetto 'LiveData' (mantiene aggiornati i dati
        automaticamente quando vengono apportate modifiche nel DB) contenente una lista di tutti i
        fumetti preferiti presenti nel database
     */
    @Query("SELECT * FROM FavouriteComic")
    fun readAllData():LiveData<List<FavouriteComic>>
}