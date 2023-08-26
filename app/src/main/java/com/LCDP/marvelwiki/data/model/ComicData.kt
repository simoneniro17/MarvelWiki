package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

//  Rappresenta la lista dei fumetti ottenuti da una risposta API
data class ComicData(
    //val limit: Int,

    //  'results' rappresenta la lista dei fumetti Marvel ottenuti dalla risposta di MarvelAPI
    @SerializedName("results")
    val results: List<Comic>? = null
)