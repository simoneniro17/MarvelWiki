package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.ComicsAPICall.LatestComicInstanceByCharId
import com.LCDP.marvelwiki.data.API.ComicsAPICall.ComicInstanceByIsbn
import com.LCDP.marvelwiki.data.API.ComicsAPICall.ComicInstanceByName
import com.LCDP.marvelwiki.data.API.ComicsAPICall.LatestComicInstance


class ComicsRepository {
    //Questo metodo chiama dalla ComicAPIbyCharId il metodo getComicsByCharId
    suspend fun getComicsByCharId(id: Int) = LatestComicInstanceByCharId.latestComicsByCharId_API.getLatestComicsByCharId(id)
    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getComicsByIsbn(isbn:String) = ComicInstanceByIsbn.comicByIsbn_API.getComicByIsbn(isbn)

    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getLatestComic() = LatestComicInstance.latestComic_API.getLatestComic()

    //Questo metodo chiama dalla ComicAPIbyIsbn il metodo getComicsByCharId
    suspend fun getComicsByName(name:String,offset:Int) = ComicInstanceByName.comicByName_API.getComicByName(name, offset = offset)
}