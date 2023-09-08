package com.lcdp.marvelwiki.data.API.CharactersAPICall

import com.lcdp.marvelwiki.data.model.CharacterResponse
import com.lcdp.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare i personaggi i cui nomi iniziano con una stringa specifica utilizzando la libreria Retrofit
interface CharacterByNameAPI {
    @GET("characters")
    suspend fun getCharacterByNameAPI(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("ts") apikey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
        @Query("offset") offset: Int
    ): Response<CharacterResponse>
}