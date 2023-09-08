package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interfaccia per recuperare gli ultimi fumetti di un personaggio specifico utilizzando la libreria Retrofit
interface LatestComicAPIByCharId {
    @GET("characters/{id}/comics")
    suspend fun getLatestComicsByCharId(
        @Path("id") id: String,
        @Query("dateDescriptor") dateDescriptor: String = "thisMonth",
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
    ):Response<ComicResponse>
}