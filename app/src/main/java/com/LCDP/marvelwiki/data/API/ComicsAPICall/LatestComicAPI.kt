package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestComicAPI {
    @GET("comics")
    suspend fun getLatestComic(
        //Specifico le componenti dell'URL
        @Query("dateDescriptor") dateDescriptor: String = "thisWeek",
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash()
    ): Response<ComicResponse>
}
