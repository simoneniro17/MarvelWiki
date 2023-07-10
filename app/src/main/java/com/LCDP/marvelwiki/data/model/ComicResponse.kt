package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

/*
    Con l'annotazione '@SerializedName' specifichiamo il nome del campo nel JSON corrispondente
    alla proprietà della nostra classe 'ComicResponse' durante il funzionamento di Gson.
 */
data class ComicResponse(

    // 'copyright' rappresenta il copyright associato ai dati restituiti dalla Marvel API
    val copyright: String,

    // 'ComicData' rappresenta i dati specifici del fumetto restituito dalla marvel API
    @SerializedName("data")
    val comicData: ComicData? = null
)