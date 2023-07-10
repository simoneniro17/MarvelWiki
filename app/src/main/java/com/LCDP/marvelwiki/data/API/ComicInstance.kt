package com.LCDP.marvelwiki.data.API

import com.LCDP.marvelwiki.usefulStuff.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ComicInstance {
    /*
        L'utilizzo della dicitura "by lazy" indica che l'inizializzazione della variabile "retrofit"
        viene ritardata fino al momento in cui viene effettivamente utilizzata, ottimizzando così
        le risorse e migliorando leggermente le prestazioni.
        Inoltre, le chiamate successive all'accesso a 'retrofit' utilizzeranno l'istanza precedentemente
        creata, senza re-inizializzarla.
     */
    private val retrofit by lazy {

        /*
            Creiamo un oggetto per la registrazione delle richieste e delle risposte di rete.
            Viene impostato il livello di log su 'BODY', che registra tutti i dettagli sia dell'header,
            sia del body.
        */
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        /*
            Creiamo un'istanza di OkHttpClient, ossia il client HTTP utilizzato da Retrofit per eseguire
            le richieste di rete.
         */
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        /*
            Creiamo l'istanza di Retrofit specificando la URL di base dell'API dei personaggi.
            Aggiungiamo un convertitore Gson per consentire a Retrofit di convertire in maniera automatica
            le risposte JSON nelle classi model. Impostiamo l'istanza di 'OkHttpClient' da utilizzare
            come client HTTP sottostante per le richieste di rete.
         */
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    val comic_api by lazy {
        // Al metodo create() passiamo l'oggetto 'Class' associato all'interfaccia 'ComicAPI'
        retrofit.create(ComicAPI::class.java)
    }
}