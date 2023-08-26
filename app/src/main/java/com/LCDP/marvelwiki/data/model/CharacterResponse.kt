package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'CharacterResponse' durante il funzionamento di Gson   */
data class CharacterResponse(

    //  'copyright' rappresenta il copyright associato ai dati restituiti dalla Marvel API
    val copyright: String,

    // 'characterData' rappresenta i dati specifici del personaggio restituito dalla Marvel API
    @SerializedName("data")
    val characterData: CharacterData? = null
)