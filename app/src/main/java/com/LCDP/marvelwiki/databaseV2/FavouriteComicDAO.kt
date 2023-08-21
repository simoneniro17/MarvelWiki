package com.LCDP.marvelwiki.databaseV2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteComicDAO {
    // Per aggiungere un comic (l'ID) ai preferiti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteComic(comic: FavouriteComic)

    // Per ottenere la lista dei comic (gli ID) preferiti
    @Query("SELECT comicID FROM favourite_comics")
    suspend fun getAllFavoriteComicId(): List<String>
}
