package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare dei fumetti tramite un titolo utilizzando la libreria Retrofit
interface ComicAPIByName {
    @GET("comics")
    suspend fun getComicByName(
        @Query("titleStartsWith") dateDescriptor: String,
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
        @Query("offset") offset: Int
    ): Response<ComicResponse>
}