package com.LCDP.marvelwiki.databaseV2

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Definizione del DB con le nostre tabelle e la versione
@Database(entities = [FavouriteCharacter::class, FavouriteComic::class, ReadComic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteCharacterDAO(): FavouriteCharacterDAO
    abstract fun favouriteComicDAO(): FavouriteComicDAO
    abstract fun readComicDAO(): ReadComicDAO
}

class MyApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "marvelwiki_database")
            .build()
    }

    // Metodo per salvare un personaggio preferito nel DB
    suspend fun saveFavouriteCharacter(characterId: String) {

        // Creazione dell'oggetto FavouriteCharacter
        val favouriteCharacter = FavouriteCharacter(characterId)

        // Ottenimento del DAO per interagire con il database
        val dao = MyApplication().database.favouriteCharacterDAO()

        // Inserimento personaggio preferito nel DB attraverso il DAO
        dao.insertFavoriteCharacter(favouriteCharacter)
    }

    // Metodo per ottenere tutti gli ID dei personaggi preferiti dal DB
    suspend fun getAllFavouriteCharacterId(): List<String> {

        // OTtenimento del DAO per interagire col DB
        val dao = MyApplication().database.favouriteCharacterDAO()

        // Ritorno della lista
        return dao.getAllFavoriteCharacterId()
    }

    // Metodo per salvare un fumetto preferito nel DB
    suspend fun saveFavouriteComic(comicId: String) {
        val favouriteComic = FavouriteComic(comicId)
        val dao = MyApplication().database.favouriteComicDAO()
        dao.insertFavoriteComic(favouriteComic)
    }

    // Metodo per ottenere tutti gli ID dei fumetti preferiti dal DB
    suspend fun getAllFavouriteComicIds(): List<String> {
        val dao = MyApplication().database.favouriteComicDAO()
        return dao.getAllFavoriteComicId()
    }

    // Metodo per salvare un fumetto letto nel DB
    suspend fun saveReadComic(comicId: String) {
        val readComic = ReadComic(comicId)
        val dao = MyApplication().database.readComicDAO()
        dao.insertReadComic(readComic)
    }

    // Metodo per ottenere tutti gli ID dei fumetti letti dal DB
    suspend fun getAllReadComicIds(): List<String> {
        val dao = MyApplication().database.readComicDAO()
        return dao.getAllReadComicId()
    }
}
