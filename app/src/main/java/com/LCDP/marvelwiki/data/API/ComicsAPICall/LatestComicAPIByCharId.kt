package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LatestComicAPIByCharId {
    // Questo metodo ci consente di ottenere i fumetti di un personaggio specifico.
    @GET("characters/{id}/comics")
    suspend fun getLatestComicsByCharId(
        /*
            L'annotazione '@Path("id")' indica che il valore dell'ID del personaggio di cui
            si vogliono ottenere i fumetti sarà sostituito nella parte dell'URL specificata.
        */
        @Path("id") id: Int,
        @Query("dateDescriptor") dateDescriptor: String = "thisMonth",
        @Query("ts") apiKey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
    ):Response<ComicResponse>
}
