package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

data class ComicData(
    @SerializedName("result")
    //<Comic>? significa che la lista dei risultati può essere nulla nel caso in cui non siano stati trovati fumetti corrispondenti alla richiesta
    val result: List<Comic>? = null
)
