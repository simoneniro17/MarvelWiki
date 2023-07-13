package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicAPIByIsbn {
    //isbn di prova: 978-1-302-94826-9
    @GET("comics")
    suspend fun getComicByIsbn(
    //Specifico le componenti dell'URL
        @Query("isbn") isbn: String,
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash()
    ): Response<ComicResponse>
}