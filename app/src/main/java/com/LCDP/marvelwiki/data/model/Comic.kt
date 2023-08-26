package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'Comic' durante il funzionamento di Gson.
    '@Expose' indica che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.   */
data class Comic(
    @SerializedName("id")
    @Expose
    var comicId: Int,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: Thumbnail? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,
) : Serializable