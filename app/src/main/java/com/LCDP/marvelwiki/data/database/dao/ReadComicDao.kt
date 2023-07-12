package com.LCDP.marvelwiki.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.data.database.model.ReadComic


//questa interfaccia definisce l'operazione di accesso e manipolazione dei dati per l'entità 'ReadComic'

@Dao
interface ReadComicDao {

    //Metodo per aggiungere un Comic letto al database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReadComic(readComic : ReadComic)
    /*
        Metodo per leggere tutti i dati della tabella "ReadComic"
        Infatti, il metodo readAllData() restituisce un oggetto 'LiveData' (mantiene aggiornati i dati
        automaticamente quando vengono apportate modifiche nel DB) contiene una lista di tutti i
        fumetti letti presenti nel database
    */

    @Query("SELECT * FROM ReadComic")
    fun readAllData(): LiveData<List<ReadComic>>
}