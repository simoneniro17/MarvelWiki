package com.LCDP.marvelwiki.databaseV2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReadComicDAO {
    // Per aggiungere un comic (l'ID) ai letti
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReadComic(comic: ReadComic)

    // Per ottenere la lista dei comic (gli ID) letti
    @Query("SELECT comicID FROM read_comics")
    suspend fun getAllReadComicId(): List<String>
}
