package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
    Con l'annotazione '@SerializedName' specifichiamo il nome del campo nel JSON corrispondente
    alla proprietà della nostra classe 'ThumbnailResponse' durante il funzionamento di Gson.

    Con l'annotazione '@Expose' indichiamo che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.
 */
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