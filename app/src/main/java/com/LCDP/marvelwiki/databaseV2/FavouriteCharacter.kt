package com.LCDP.marvelwiki.databaseV2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_characters")
data class FavouriteCharacter (
    @PrimaryKey val characterId: String
)