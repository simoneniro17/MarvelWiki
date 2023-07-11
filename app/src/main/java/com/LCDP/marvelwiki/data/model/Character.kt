package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
    Utilizziamo una data class perché //TODO

    Con l'annotazione '@SerializedName' specifichiamo il nome del campo nel JSON corrispondente
    alla proprietà della nostra classe 'Character' durante il funzionamento di Gson.

    Con l'annotazione '@Expose' indichiamo che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.
 */
data class Character (
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,
) : Serializable