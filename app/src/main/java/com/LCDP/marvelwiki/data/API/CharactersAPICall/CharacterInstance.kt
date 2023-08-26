package com.LCDP.marvelwiki.data.API.CharactersAPICall

import com.LCDP.marvelwiki.usefulStuff.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterInstance {

    // Istanza di Retrofit per le chiamate alle API dei personaggi
    private val retrofit by lazy {

        // Creazione di un interceptor per registrare i dettagli delle richieste e delle risposte di rete
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Configurazione di OkHttpClient per le richieste di rete con log dettagliato
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Configurazione e creazione dell'istanza Retrofit
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL) // URL di base delle API
            .addConverterFactory(GsonConverterFactory.create()) // Conversione automatica JSON in classi model
            .client(client) // Utilizzo del client HTTP configurato
            .build()
    }

    // Istanze delle diverse interfacce API per le chiamate ai diversi endpoint
    val char_api by lazy {
        retrofit.create(CharacterAPI::class.java) // Elenco dei personaggi
    }

    val charByName_api by lazy {
        retrofit.create(CharacterByNameAPI::class.java) // Elenco personaggi per nome
    }

    val charById_api by lazy {
        retrofit.create(CharacterAPIById::class.java) // Personaggio per ID
    }
}