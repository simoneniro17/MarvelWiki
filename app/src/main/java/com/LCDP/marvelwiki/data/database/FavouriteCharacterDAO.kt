package com.LCDP.marvelwiki.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteCharacterDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter)

    @Query("SELECT * FROM FavouriteCharacter")
    fun readAllData(): LiveData<List<FavouriteCharacter>>


}