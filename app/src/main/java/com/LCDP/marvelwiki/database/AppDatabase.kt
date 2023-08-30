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

// Specifichiamo le entità coinvolte nel database, la versione del database e altre opzioni
@Database(
    entities = [FavouriteCharacter::class, FavouriteComic::class, ReadComic::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Definiamo il metodo per ottenere il DAO associato al database
    abstract fun favouriteCharacterDAO(): FavouriteCharacterDAO
    abstract fun favouriteComicDAO(): FavouriteComicDAO
    abstract fun readComicDAO(): ReadComicDAO

    // Creiamo il Singleton per l'accesso al database
    companion object {

        // Assicura la coerenza dei dati tra i thread
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Funzione per ottenere un'istanza del database
        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE

            // Verifichiamo se esiste un'istanza già creata
            return if (tempInstance != null) {
                tempInstance
            } else {

                // Creiamo una nuova istanza del database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marvelwiki_database"
                ).build()

                // Assegniamo l'istanza appena creata alla variabile 'INSTANCE'
                INSTANCE = instance
                instance
            }
        }
    }
}