package com.LCDP.marvelwiki.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//Definiamo una classe che verrà utilizzata come entità nel database SQLite tramite room
//Specifichiamo il nome della tabella associata a questa entità

@Entity(tableName = "ReadComic")
data class ReadComic(
        @PrimaryKey
        var id: Int,
        var title: String?,
        var thumbnailPath: String?,
        var description: String?
)