package com.LCDP.marvelwiki.databaseV2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_comics")
data class FavouriteComic (
    @PrimaryKey val comicId: String
)