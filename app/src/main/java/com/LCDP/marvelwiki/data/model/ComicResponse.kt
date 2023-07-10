package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName
data class ComicResponse(
    //coyright rappresenta il copyright associato ai dati restituiti dalla marvel API
    val copyright: String,
    @SerializedName("data")
    //rappresenta i dati specifici del fumetto restituiti dalla marvel API
    //ComicData? significa che può essere nulla enl caso in cui non siano stati trovati fumetti corrispondenti alla richiesta
    val comicData: ComicData? = null
)