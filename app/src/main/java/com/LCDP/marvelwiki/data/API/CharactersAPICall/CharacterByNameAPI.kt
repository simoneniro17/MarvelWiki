package com.LCDP.marvelwiki.data.API.CharactersAPICall

import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterByNameAPI {
    // Specifichiamo l'endpoint dell'API per ottenere i personaggi.
    @GET("characters")
    suspend fun getCharacterByNameAPI(

        // La funzione prende in input diversi parametri, passati come parametri di query nell'URL della richiesta.
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("ts") apikey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
        @Query("offset") offset: Int
    ): Response<CharacterResponse>
}