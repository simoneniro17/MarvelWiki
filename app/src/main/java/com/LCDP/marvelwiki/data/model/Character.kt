package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'Character' durante il funzionamento di Gson.
    '@Expose' indica che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.   */
data class Character(
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