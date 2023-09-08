package com.lcdp.marvelwiki.data.API.CharactersAPICall

import com.lcdp.marvelwiki.data.model.CharacterResponse
import com.lcdp.marvelwiki.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaccia per recuperare i personaggi utilizzando la libreria Retrofit
interface CharacterAPI {

    // Per ottenere l'elenco dei personaggi, specifichiamo l'endpoint dell'API
    @GET("characters")
    suspend fun getCharacters(
        // Parametri di query utilizzati per la richiesta
        @Query("ts") apikey: String = Constant.ts,
        @Query("apikey") ts: String = Constant.PUBLIC_KEY,
        @Query("hash") hash: String = Constant.hash(),
        @Query("limit") limit: Int = Constant.limit,
        @Query("offset") offset: Int
    ): Response<CharacterResponse>
    // Risposta della chiamata API contenente i dati dei personaggi
}