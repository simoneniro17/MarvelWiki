package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.SerializedName

/*
    Questa classe rappresenta i dati associati ai personaggi Marvel ottenuti da una risposta API,
    come il limite di risultati e la lista dei personaggi stessi.
 */
data class CharacterData(
    // val limit: Int,

    /*
        La notazione '@SerializedName' viene utilizzata per specificare il nome del campo nel JSON
        corrispondente al nostro attributo 'results' durante la serializzazione e la deserializzazione con Gson.

        'results' rappresenta la lista dei personaggi Marvel ottenuti dalla risposta di MarvelAPI.
     */
    @SerializedName("results")
    val results: List<Character>? = null
)