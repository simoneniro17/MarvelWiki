package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName
data class CharacterResponse (
    //Copyright rappresenta il copyright associato ai dati restituiti dalla Marvel API
    val copyright:String,
    //CharacterData rappresenta i dati specifici del personaggio resittuito dalla marvel APIf

    @SerializedName("data")                         //la proprietà SerializedName specifica il che il nomme dell'oggetto JSON corrispondente è "data"
    val characterData: CharacterData?= null         //CharacterData? sta a significare che può essere nulla nel caso in uci non siano stati trovati personaggi corrispondenti alla richiesta
)