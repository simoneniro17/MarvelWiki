package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

//  Rappresenta i dati associati ai personaggi Marvel ottenuti da una risposta API
data class CharacterData(
    // val limit: Int,

    //  'results' rappresenta la lista dei personaggi Marvel ottenuti dalla risposta di MarvelAPI
    @SerializedName("results")
    val results: List<Character>? = null
)