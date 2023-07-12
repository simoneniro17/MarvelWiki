package com.LCDP.marvelwiki.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.LCDP.marvelwiki.data.model.Thumbnail

@Entity(tableName = "FavouriteCharacter")
data class FavouriteCharacter (
    @PrimaryKey
    var id: Int,
    var name: String?,
    var thumbnailPath: String?,
    var description: String?
        )
