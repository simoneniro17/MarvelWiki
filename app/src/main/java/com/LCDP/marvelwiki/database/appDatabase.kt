package com.LCDP.marvelwiki.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.LCDP.marvelwiki.database.dao.FavouriteCharacterDAO
import com.LCDP.marvelwiki.database.dao.FavouriteComicDAO
import com.LCDP.marvelwiki.database.dao.ReadComicDAO
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.model.ReadComic

// Nella notazione '@Database' specifichiamo le entità coinvolte nel database, la versione del database e altre opzioni
@Database(
    entities = [FavouriteCharacter::class, FavouriteComic::class, ReadComic::class],
    version = 1,
    exportSchema = false
)
abstract class appDatabase : RoomDatabase() {

    // Definiamo il metodo per ottenere il DAO associato al database
    abstract fun favouriteCharacterDAO(): FavouriteCharacterDAO
    abstract fun favouriteComicDAO(): FavouriteComicDAO
    abstract fun readComicDAO(): ReadComicDAO

    // Creiamo il Singleton per l'accesso al database
    companion object {
        @Volatile // INUTILE?? assicura la coerenza dei dati tra i thread
        private var INSTANCE: appDatabase? = null

        // Funzione per ottenere un'istanza del database
        fun getDatabase(context: Context): appDatabase {

            // Verifichiamo se esiste un'istanza già creata
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                // Creiamo una nuova istanza del database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "app_Database"
                ).build()

                // Assegniamo l'istanza appena creata alla variabile 'INSTANCE'
                INSTANCE = instance
                return instance
            }
        }
    }
}