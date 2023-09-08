package com.lcdp.marvelwiki.data.API.ComicsAPICall

import com.lcdp.marvelwiki.data.model.ComicResponse
import com.lcdp.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare l'ultimo fumetto uscito utilizzando la libreria Retrofit
interface LatestComicAPI {
    @GET("comics")
    suspend fun getLatestComic(
        @Query("dateDescriptor") dateDescriptor: String = "thisWeek",
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash()
    ): Response<ComicResponse>
}