package com.LCDP.marvelwiki.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.LCDP.marvelwiki.data.model.Thumbnail


//  Definiamo una classe che verrà utilizzata come entità nel database SQLite tramite Room
//  Specifichiamo il nome della tabella associata a questa entità
@Entity(tableName = "FavouriteCharacter")
data class FavouriteCharacter (
    @PrimaryKey
    var id: Int,
    var name: String?,
    var thumbnailPath: String?,
    var description: String?
        )
