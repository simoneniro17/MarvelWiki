package com.lcdp.marvelwiki.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*  Classe che verrà utilizzata come entità nel database SQLite tramite Room
    Specifichiamo il nome della tabella associata a questa entità */
@Entity(tableName = "favourite_character")
data class FavouriteCharacter(
    @PrimaryKey val characterId: String
)