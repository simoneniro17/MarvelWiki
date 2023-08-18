package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.ComicsAPICall.ComicInstance


class ComicsRepository {
    //Questo metodo chiama dalla ComicAPIbyCharId il metodo getComicsByCharId
    suspend fun getComicsByCharId_api(id: Int) = ComicInstance.latestComicByCharId_api.getLatestComicsByCharId(id)
    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getComicsByIsbn_api(isbn:String) = ComicInstance.comicsByIsbn_api.getComicByIsbn(isbn)

    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getLatestComic_api() = ComicInstance.latestComic_api.getLatestComic()

    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getComicsByName_api(name:String,offset:Int) = ComicInstance.comicsByName_api.getComicByName(name, offset = offset)
}