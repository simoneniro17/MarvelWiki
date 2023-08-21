package com.LCDP.marvelwiki.databaseV2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "read_comics")
data class ReadComic (
    @PrimaryKey val comicId: String
)