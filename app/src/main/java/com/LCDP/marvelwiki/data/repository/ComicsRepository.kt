package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.ComicInstance

class ComicsRepository {

    //  Questo metodo chiama dalla API il metodo getComics()
    suspend fun getComics(id: Int,offset: Int) = ComicInstance.comic_api.getComics(id, offset = offset)
}