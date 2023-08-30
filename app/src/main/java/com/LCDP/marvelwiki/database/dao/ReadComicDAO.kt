package com.LCDP.marvelwiki.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LCDP.marvelwiki.database.model.ReadComic

// Interfaccia che definisce le operazioni di accesso e manipolazione dei dati per l'entità 'read_comic'
@Dao
interface ReadComicDAO {

    // Metodo per aggiungere (l'ID di) un comic ai fumetti letti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReadComic(readComic: ReadComic)

    // Metodo per rimuovere un fumetto letto dal database
    @Delete
    suspend fun deleteReadComic(readComic: ReadComic)

    // Metodo per ottenere la lista (degli ID) dei comic letti
    @Query("SELECT * FROM read_comic")
    fun getAllReadComicId(): List<String>
}