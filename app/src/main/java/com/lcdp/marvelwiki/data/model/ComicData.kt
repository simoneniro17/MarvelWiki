package com.lcdp.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

//  Rappresenta la lista dei fumetti ottenuti da una risposta API
data class ComicData(
    //  'results' rappresenta la lista dei fumetti Marvel ottenuti dalla risposta di MarvelAPI
    @SerializedName("results")
    val results: List<Comic>? = null
)