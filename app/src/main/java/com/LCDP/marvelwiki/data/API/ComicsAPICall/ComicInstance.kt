package com.LCDP.marvelwiki.data.API.ComicsAPICall

import com.LCDP.marvelwiki.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ComicInstance {

    // Istanza di Retrofit per le chiamate alle API dei fumetti
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
    val comicsById_api: ComicAPIById by lazy {
        retrofit.create(ComicAPIById::class.java)   // Fumetto per ID
    }

    val comicsByIsbn_api: ComicAPIByIsbn by lazy {
        retrofit.create(ComicAPIByIsbn::class.java)   // Fumetto per ISBN
    }

    val comicsByName_api: ComicAPIByName by lazy {
        retrofit.create(ComicAPIByName::class.java)   // Fumetti per titolo
    }

    val latestComic_api: LatestComicAPI by lazy {
        retrofit.create(LatestComicAPI::class.java)   // Ultimo fumetto uscito
    }

    val latestComicByCharId_api: LatestComicAPIByCharId by lazy {
        retrofit.create(LatestComicAPIByCharId::class.java)   // Ultimi fumetti di un personaggio
    }
}