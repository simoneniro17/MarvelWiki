package com.LCDP.marvelwiki.data.API.CharactersAPICall

import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interfaccia per recuperare un personaggio specifico tramite ID utilizzando la libreria Retrofit
interface CharacterAPIById {
    @GET("characters/{characterId}")
    suspend fun getCharacterByIdAPI(
        @Path("characterId") id: String,
        @Query("ts") apikey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
    ): Response<CharacterResponse>
}