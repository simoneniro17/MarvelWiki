package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare un fumetto specifico tramite ISBN utilizzando la libreria Retrofit --- ISBN di prova: 978-1-302-94826-9
interface ComicAPIByIsbn {
    @GET("comics")
    suspend fun getComicByIsbn(
        @Query("isbn") isbn: String,
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash()
    ): Response<ComicResponse>
}