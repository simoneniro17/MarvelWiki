package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
    Utilizziamo una data class perché //TODO

    Con l'annotazione '@SerializedName' specifichiamo il nome del campo nel JSON corrispondente
    alla proprietà della nostra classe 'Comic' durante il funzionamento di Gson.

    Con l'annotazione '@Expose' indichiamo che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.
 */
data class Comic(
    @SerializedName("id")
    @Expose
    var comicId: Int? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,
    var images: List<Images>
) :Serializable

//:Serializable indica che la classe comic può essere serializzata, ciò significa che gli oggetti della classe comic
//possono essere convertiti in un flusso di byte per essere trasferiti attravero la rete, salvati su disco
// o in qualsiasi altro contesto in cui sia necessaria la persistenza dei dati