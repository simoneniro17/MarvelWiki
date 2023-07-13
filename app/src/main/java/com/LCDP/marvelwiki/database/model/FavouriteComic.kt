package com.LCDP.marvelwiki.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//Definiamo una classe che verrà utilizzata come entità nel database SQLite tramite room
//Specifichiamo il nome della tabella associata a questa entità

@Entity(tableName = "FavouriteComic")
data class FavouriteComic(
    @PrimaryKey
    var id: Int,
    var title: String?,
    var thumbnailPath: String?,
    var description: String?,
    var images: String?
)