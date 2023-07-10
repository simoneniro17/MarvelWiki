package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

/*
    Questa classe rappresenta i dati associati ai fumetti Marvel ottenuti da una risposta API,
    ossia la lista dei fumetti stessi.
 */
data class ComicData(

    /*
        La notazione '@SerializedName' viene utilizzata per specificare il nome del campo nel JSON
        corrispondente al nostro attributo 'result' durante la serializzazione e la deserializzazione con Gson.
        'result' rappresenta la lista dei fumetti Marvel ottenuti dalla risposta di MarvelAPI.
    */
    @SerializedName("result")
    val result: List<Comic>? = null
)