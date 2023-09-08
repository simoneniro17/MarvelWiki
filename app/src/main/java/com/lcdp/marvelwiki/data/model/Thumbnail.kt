package com.lcdp.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'Thumbnail' durante il funzionamento di Gson.
    '@Expose' indica che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.   */
data class Thumbnail(

    // 'path' indica il percorso dell'immagine associata ad un elemento specifico della Marvel API
    @SerializedName("path")
    @Expose
    var path: String? = null,

    // 'extension' indica l'estensione dell'immagine associata ad un elemento specifico della Marvel API
    @SerializedName("extension")
    @Expose
    var extension: String? = null
)