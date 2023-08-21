package com.LCDP.marvelwiki.databaseV2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteCharacterDAO {
    // Per aggiungere un personaggio (l'ID) ai preferiti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteCharacter(character: FavouriteCharacter)

    // Per ottenere la lista dei personaggi (gli ID) preferiti
    @Query("SELECT characterId FROM favourite_characters")
    suspend fun getAllFavoriteCharacterId(): List<String>
}
