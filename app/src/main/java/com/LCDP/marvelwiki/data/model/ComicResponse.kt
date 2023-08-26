package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'ComicResponse' durante il funzionamento di Gson   */
data class ComicResponse(

    //  'copyright' rappresenta il copyright associato ai dati restituiti dalla Marvel API
    val copyright: String,

    // 'comicData' rappresenta i dati specifici del fumetto restituito dalla Marvel API
    @SerializedName("data")
    val comicData: ComicData? = null
)