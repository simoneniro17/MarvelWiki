package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interfaccia per recuperare un fumetto specifico tramite ID utilizzando la libreria Retrofit
interface ComicAPIById {
    @GET("comics/{comicId}")
    suspend fun getComicByIdAPI(
        @Path("comicId") id: String,
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit
    ): Response<ComicResponse>
}