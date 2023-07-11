package com.LCDP.marvelwiki.data.API

import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.usefulStuff.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare i personaggi utilizzando la libreria Retrofit.
interface CharacterAPI {

    // Specifichiamo l'endpoint dell'API per ottenere i personaggi.
    @GET("characters")
    suspend fun getCharacters(
        //TODO INVERTIRE TS E APIKEY???
        // La funzione prende in input diversi parametri, passati come parametri di query nell'URL della richiesta.
        @Query("ts") apikey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit
    ):Response<CharacterResponse>
}